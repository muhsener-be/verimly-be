package app.verimly.task.adapter.web.docs;

public class TaskApiExamples {

    public static final String TASK_NOT_FOUND =
            """
            {
                "timestamp", "2025-09-03T10:45:21.985991Z",
                "status", 404,
                "path": "uri=/api/v1/resource",
                "message", "No such Task found with provided ID: 'd290f1ee-6c54-4b01-90e6-d701748f0851'",
                "error_code", "resource-not-found",
                "resourceType", "TASK",
                "resourceId", "d290f1ee-6c54-4b01-90e6-d701748f0851"
             }
            """;


    public static final String FOLDER_NOT_FOUND =
            """
            {
                "timestamp", "2025-09-03T10:45:21.985991Z",
                "status", 404,
                "path": "uri=/api/v1/resource",
                "message", "No such Folder found with provided ID: 'd290f1ee-6c54-4b01-90e6-d701748f0851'",
                "error_code", "resource-not-found",
                "resourceType", "FOLDER",
                "resourceId", "d290f1ee-6c54-4b01-90e6-d701748f0851"
             }
            """;

    public static final String SESSION_NOT_FOUND =
            """
            {
                "timestamp", "2025-09-03T10:45:21.985991Z",
                "status", 404,
                "path": "uri=/api/v1/resource",
                "message", "No such Session found with provided ID: 'd290f1ee-6c54-4b01-90e6-d701748f0851'",
                "error_code", "resource-not-found",
                "resourceType", "SESSION",
                "resourceId", "d290f1ee-6c54-4b01-90e6-d701748f0851"
             }
            """;
}
