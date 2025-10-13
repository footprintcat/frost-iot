# Rules

## Conversation Rules

When answering my questions, please follow the rules:
1. Please use the system default language to answer my questions.

When providing suggestions, please follow the rules:
1. Mark suggestions with numerical sequence numbers. Users can subsequently make corresponding adjustments by entering a sequence number.

## Code Edit Rules

Before editing the code:
1. Do not act directly. First, present the plan and proceed only after receiving confirmation.

When editing the code, please follow the rules:
1. Do not remove any existing comments.
2. Do not add any new comments that are not necessary.
3. If you need to update the code comments, please try to keep them in the same format as the original and adhere to the principle of minimal changes to ensure that the Git history is clear.
4. Do not change the indentation or spacing of the code.
5. Do not change the functionality of the code.
6. Do not change the code that is not related to the current task.
7. You should follow the code style below.

After editing the code:
1. Do not need to run command to test the modification unless user required you to do.

## Code Style

Common code style:
1. When generating new lines, please trim the end of the line.

Java code style:
1. `@since` should use today's date (the current time), not the date from other Java class comments.

## Markdown Edit Rules

When you edit a markdown file, you should read the entire file first, and edit only the necessary part. DO NOT rewrite the entire file.

## Git Commit

After you finish the code, please generate a git commit message. The commit should follow the history format and use Simplified Chinese.
