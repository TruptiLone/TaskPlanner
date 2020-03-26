package com.trupti.eventplanner.eventplanner.task;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.*;


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
                        .append(sqlRowSet.getString("created_at"))
                        .append("\n");
            }
            return ("SELECT * from task_info:\n" + resultStr);
        }


        @CrossOrigin
        @RequestMapping(value = "/addTask", method = RequestMethod.POST)
        public String addUser(@RequestBody TaskData addTaskDatarData) {
            JdbcTemplate jdbcTemplate = JDBCConnector.getJdbcTemplate();
            String queryStr = "INSERT INTO task_info (task_name, task_description, Task_date) " +
                    "VALUES (" +
                    "'" + addTaskDatarData.getTaskName() + "'," +
                    "'" + addTaskDatarData.getTaskDescription() + "'," +
                    "'" + addTaskDatarData.getDate() + "'" +
                    ");";
            int rowsUpdated = jdbcTemplate.update(queryStr);
            return ("Rows updated: " + rowsUpdated);
        }


}
