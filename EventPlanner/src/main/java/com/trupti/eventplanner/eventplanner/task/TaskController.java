package com.trupti.eventplanner.eventplanner.task;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



@RestController
    public class TaskController {

        @CrossOrigin
        @RequestMapping(value = "/helloworld", method = RequestMethod.GET)
        public String printHelloWorld() {
            return "Hello World";
        }

        @CrossOrigin
        @RequestMapping(value = "/printAllTasks", method = RequestMethod.GET)
        public String printAllTasks() {
            JdbcTemplate jdbcTemplate = JDBCConnector.getJdbcTemplate();
            StringBuilder resultStr = new StringBuilder();

            String queryStr = "SELECT * from task_info;";
            SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(queryStr);
            while (sqlRowSet.next()) {
                resultStr.append(sqlRowSet.getString("task_id")).append(", ")
                        .append(sqlRowSet.getString("task_name")).append(", ")
                        .append(sqlRowSet.getString("task_description")).append(", ")
                        .append(sqlRowSet.getString("Task_date")).append(", ")
                        .append(sqlRowSet.getString("mark_as_done")).append(", ")
                        .append(sqlRowSet.getString("created_at"))
                        .append("\n");
            }
            return ("SELECT * from task_info:\n" + resultStr);
        }


        @CrossOrigin
        @RequestMapping(value = "/addTask", method = RequestMethod.POST)
        public ResponseEntity<Object> addTask(@Valid @RequestBody TaskData addTaskData) {

            JdbcTemplate jdbcTemplate = JDBCConnector.getJdbcTemplate();
            String queryStr = "INSERT INTO task_info (task_name, task_description, Task_date) " +
                    "VALUES (" +
                    "'" + addTaskData.getTaskName() + "'," +
                    "'" + addTaskData.getTaskDescription() + "'," +
                    "'" + addTaskData.getDate() + "'" +
                    ");";
            KeyHolder keyHolder = new GeneratedKeyHolder();
           jdbcTemplate.update(new PreparedStatementCreator() {
               @Override
               public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                   PreparedStatement ps =connection.prepareStatement(queryStr,new String[]{"task_id"});

                   return ps;
               }
           },keyHolder);
            // int rowsUpdated = jdbcTemplate.update(queryStr);


            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/"+(keyHolder.getKey())).buildAndExpand(addTaskData.getTaskId()).toUri();

            return ResponseEntity.created(location).build();


           // return ("Rows updated: " + rowsUpdated);
        }

    @CrossOrigin
    @RequestMapping(value = "/searchTask/{id}", method = RequestMethod.GET)
    public String searchTask(@PathVariable Integer id) {

        JdbcTemplate jdbcTemplate = JDBCConnector.getJdbcTemplate();
        StringBuilder resultStr = new StringBuilder();
        String queryStr = "select * from task_info where task_id = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(queryStr, id);

              if (sqlRowSet.next()) {
                  resultStr.append(sqlRowSet.getString("task_id")).append(", ")
                          .append(sqlRowSet.getString("task_name")).append(", ")
                          .append(sqlRowSet.getString("task_description")).append(", ")
                          .append(sqlRowSet.getString("Task_date")).append(", ")
                          .append(sqlRowSet.getString("mark_as_done")).append(", ")
                          .append(sqlRowSet.getString("created_at"))
                          .append("\n");
              }
              else {
                  throw new TaskNotFoundException("Invalid input for Task Id");
              }

        return resultStr.toString();
    }



        @CrossOrigin
            @RequestMapping(value = "/markTaskAsDone/{id}", method = RequestMethod.POST)
        public String updateTask(@PathVariable Integer id) {

            JdbcTemplate jdbcTemplate = JDBCConnector.getJdbcTemplate();
            String queryStr = "UPDATE task_info SET mark_as_done = 1 WHERE task_id = ?";
            int rowsUpdated = jdbcTemplate.update(queryStr,id);
            if(rowsUpdated==0){
                throw new TaskNotFoundException("Invalid input for Task Id");
            }
            return ("Rows updated: " + rowsUpdated);
        }

        @CrossOrigin
        @RequestMapping(value = "/deleteTask/{id}", method = RequestMethod.DELETE)
        public String deleteTask(@PathVariable Integer id) {

            JdbcTemplate jdbcTemplate = JDBCConnector.getJdbcTemplate();
            String queryStr = "DELETE FROM task_info WHERE task_id = ?";
            int rowsUpdated = jdbcTemplate.update(queryStr,id);
            if(rowsUpdated==0){
                throw new TaskNotFoundException("Invalid input for Task Id");
            }
            return ("Rows updated: " + rowsUpdated);
        }



}
