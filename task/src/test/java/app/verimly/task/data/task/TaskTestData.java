package app.verimly.task.data.task;

import app.verimly.commons.core.utils.MyStringUtils;
import app.verimly.task.domain.vo.task.TaskDescription;
import app.verimly.task.domain.vo.task.TaskName;

public class TaskTestData {


    private static final TaskTestData INSTANCE = new TaskTestData();

    public static TaskTestData getInstance() {
        return INSTANCE;
    }


    public String tooLongNameValue() {
        return MyStringUtils.generateString(TaskName.MAX_LENGTH + 1);
    }

    public String tooLongDescriptionValue() {
        return MyStringUtils.generateString(TaskDescription.MAX_LENGTH + 1);
    }
}
