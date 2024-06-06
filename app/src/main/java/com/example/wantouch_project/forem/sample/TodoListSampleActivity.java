package com.example.wantouch_project.forem.sample;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wantouch_project.forem.activitys.ForemActivity;
import com.example.wantouch_project.forem.extensions.ForemList;
import com.example.wantouch_project.forem.ui.ForemListView;
import com.example.wantouch_project.forem.views.HorizontalLayout;
import com.example.wantouch_project.forem.views.Root;

public class TodoListSampleActivity extends ForemActivity {
    @Override
    public void render(Root root) {
        var todoList = var(new ForemList<String>());
        space(match_parent, 0, 1);
        var taskEditText = varType(EditText.class);
        this.<HorizontalLayout>create(() -> {
            layout(match_parent, 0, 10);
        }).render(() -> {
            this.<EditText>create(taskEditText, () -> {
                layout(0, match_parent, 70);
                hint("タスクを入力");
                textSize(24);
            });
            this.<Button>create(() -> {
                layout(0, match_parent, 30);
                text("追加");
                textSize(24);
                onClick(() -> {
                    var taskName = taskEditText[v].getText().toString();
                    if (taskName.equals(""))
                        return;
                    taskEditText[v].setText("");
                    todoList[v].add(taskName);
                    ignite(todoList);
                });
            });
        });
        ForemListView.create(() -> {
            layout(match_parent, 0, 89);
        }).render(() -> {
            onChange(todoList, () -> {
                clearChild();
                todoList[v].forEach((todo, index) -> {
                    this.<HorizontalLayout>create(() -> {
                        layout(match_parent, wrap_content);
                    }).render(() -> {
                        this.<TextView>create(() -> {
                            layout(0, match_parent, 60);
                            text(todo);
                            textSize(26);
                        });
                        this.<Button>create(() -> {
                            layout(0, match_parent, 40);
                            text("完了");
                            textSize(18);
                            onClick(() -> {
                                todoList[v].remove(index);
                                ignite(todoList);
                            });
                        });
                    });
                });
            });
        });
    }
}
