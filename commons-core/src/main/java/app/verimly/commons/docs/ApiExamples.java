package app.verimly.commons.docs;

public class ApiExamples {

    public static final String FORBIDDEN =  """
                                        {
                                            "timestamp": "2025-09-04T13:58:08.475372Z",
                                            "status": 403,
                                            "path": "uri=/api/v1/resource",
                                            "message": "Principal 50ac87d9-dab0-4c65-bd14-41a91de036df has no permission to perform LIST_FOLDER on folder:f17b9134-4a46-489a-aee1-9ded67e3f688 (required: OWNERSHIP)",
                                            "principal": "50ac87d9-dab0-4c65-bd14-41a91de036df",
                                            "action": "LIST_FOLDER",
                                            "resource": "folder:f17b9134-4a46-489a-aee1-9ded67e3f688",
                                            "requirement": "OWNERSHIP",
                                            "error_code": "forbidden"
                                        }
                                        """;

    public static final String BAD_REQUEST = """
                                            {
                                                "timestamp": "2025-09-03T10:45:21.985991Z",
                                                "status": 400,
                                                "path": "/api/v1/resource",
                                                "message": "invalid input",
                                                "error_code": "bad-request",
                                                "error_codes":{
                                                    "folder.name-length": "Folder name can be at most x characters.",
                                                    "folder.description-length": "Folder description can be at most x characters.",
                                                    "color.hex-format": "Label color must be in hex format."
                                                }
                                            }
                                            """;
}
