package app.verimly.commons.core.docs;

public class ApiExamples {

    public static final String FOLDER_NOT_FOUND = """
            {
                "timestamp": "2025-08-28T18:44:25.503833Z",
                "status": 404,
                "error": "folder.not-found",
                "message": "Folder not found with provided ID: 49fb32c7-5b8b-4991-86a0-9c64d4c84679",
                "path": "uri=/api/v1/tasks",
                "additional": null
            }
            """;
    public static final String UNAUTHORIZED = """
                {
                    "timestamp": "2025-08-28T18:10:53.287668Z",
                    "status": 401,
                    "error": "Unauthorized",
                    "message": "Full authentication is required to access this resource",
                    "path": "/api/v1/folders",
                    "additional": null
                }
            """;
    public static final String INTERNAL = """
                {
                    "timestamp": "2025-08-28T18:10:53.287668Z",
                    "status": 500,
                    "error": "internal",
                    "message": "Unexpected error happened.",
                    "path": "/api/v1/folders",
                    "additional": null
                }
            """;

}
