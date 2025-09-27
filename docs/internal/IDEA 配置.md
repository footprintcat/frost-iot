# IDEA 配置

## 需要手动配置的配置项

> 以下配置未提交到代码仓库中，需手动进行配置

`未在 .idea 目录中的配置项`

```xml
<!-- 设置 - 编辑器 - 常规 - 自动导入 - Java - 动态添加明确的 import -->
<!-- docs:
  自动导入 - 自动添加导入语句
  Auto import - Automatically add import statements
  - zh: https://www.jetbrains.com/zh-cn/help/idea/creating-and-optimizing-imports.html#automatically-add-import-statements
  - en: https://www.jetbrains.com/help/idea/creating-and-optimizing-imports.html#automatically-add-import-statements
  自动导入 - Java
  Auto Import - Java
  - zh: https://www.jetbrains.com/zh-cn/help/idea/settings-auto-import.html#java
  - en: https://www.jetbrains.com/help/idea/settings-auto-import.html#java
-->
```

`.idea/workspace.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  ...
  <!-- 设置 - 编辑器 - 常规 - 自动导入 - Java - 动态优化 import -->
  <!-- docs:
    自动导入 - 实时优化导入
    Auto import - Optimize imports on the fly
    - zh: https://www.jetbrains.com/zh-cn/help/idea/creating-and-optimizing-imports.html#-en0wtk_154
    - en: https://www.jetbrains.com/help/idea/creating-and-optimizing-imports.html#-9qycw4_155
    自动导入 - Java
    Auto Import - Java
    - zh: https://www.jetbrains.com/zh-cn/help/idea/settings-auto-import.html#java
    - en: https://www.jetbrains.com/help/idea/settings-auto-import.html#java
  -->
  <component name="CodeInsightWorkspaceSettings">
    <option name="optimizeImportsOnTheFly" value="true" />
  </component>
  ...
</project>
```

## 已提交到代码仓库的配置项

`.idea/codeStyles/codeStyleConfig.xml`

```xml
<component name="ProjectCodeStyleConfiguration">
  <state>
    <!-- 设置 - 编辑器 - 代码样式 - 方案：项目 -->
    <option name="USE_PER_PROJECT_SETTINGS" value="true" />
  </state>
</component>
```

`.idea/codeStyles/Project.xml`

```xml
<component name="ProjectCodeStyleConfiguration">
  <code_scheme name="Project" version="173">
    <JavaCodeStyleSettings>
      <option name="CLASS_COUNT_TO_USE_IMPORT_ON_DEMAND" value="9999" />
      <option name="NAMES_COUNT_TO_USE_IMPORT_ON_DEMAND" value="9999" />
    </JavaCodeStyleSettings>
    <codeStyleSettings language="Groovy">
      <!-- 设置 - 编辑器 - 代码样式 - Groovy - 代码生成 - 行注释在第一列 -->
      <option name="LINE_COMMENT_AT_FIRST_COLUMN" value="false" />
      <!-- 设置 - 编辑器 - 代码样式 - Groovy - 代码生成 - 在行注释开始处添加空格 -->
      <option name="LINE_COMMENT_ADD_SPACE" value="true" />
      <!-- 设置 - 编辑器 - 代码样式 - Groovy - 代码生成 - 重新设置格式时强制 -->
      <option name="LINE_COMMENT_ADD_SPACE_ON_REFORMAT" value="true" />
      <!-- 设置 - 编辑器 - 代码样式 - Groovy - 换行和大括号 - 重新设置格式时保持 - 注释在第一列 -->
      <option name="KEEP_FIRST_COLUMN_COMMENT" value="false" />
    </codeStyleSettings>
    <codeStyleSettings language="JAVA">
      <!-- 设置 - 编辑器 - 代码样式 - Java - 代码生成 - 行注释在第一列 -->
      <option name="LINE_COMMENT_AT_FIRST_COLUMN" value="false" />
      <!-- 设置 - 编辑器 - 代码样式 - Java - 代码生成 - 在行注释开始处添加空格 -->
      <option name="LINE_COMMENT_ADD_SPACE" value="true" />
      <!-- 设置 - 编辑器 - 代码样式 - Java - 代码生成 - 重新设置格式时强制 -->
      <option name="LINE_COMMENT_ADD_SPACE_ON_REFORMAT" value="true" />
      <!-- 设置 - 编辑器 - 代码样式 - Java - 换行和大括号 - 重新设置格式时保持 - 注释在第一列 -->
      <option name="KEEP_FIRST_COLUMN_COMMENT" value="false" />
    </codeStyleSettings>
      <!-- 设置 - 编辑器 - 代码样式 - XML - 代码生成 - 行注释在第一列 -->
    <codeStyleSettings language="XML">
      <option name="LINE_COMMENT_AT_FIRST_COLUMN" value="false" />
    </codeStyleSettings>
  </code_scheme>
</component>
```

`.idea/dictionaries/project.xml`

```xml
<component name="ProjectDictionaryState">
  <!-- 设置 - 编辑器 - 自然语言 - 拼写 - 接受的单词 -->
  <dictionary name="project">
    <words>
      <!-- -Dfile.encoding=utf-8 命令 -->
      <w>Dfile</w>
      <!-- PostgreSQL 数据库名称 -->
      <w>PostgreSQL</w>
      <!-- application.yml 中 datasources 配置 -->
      <w>datasources</w>
      <!-- 公司英文名 -->
      <w>footprintcat</w>
      <!-- lombok 包名：io.freefair.lombok -->
      <w>freefair</w>
      <!-- 产品英文名 -->
      <w>frostiot</w>
      <!-- PostgreSQL 数据库 -->
      <w>postgre</w>
      <!-- application.yml 中 rapidoc 配置 -->
      <w>rapidoc</w>
    </words>
  </dictionary>
</component>
```

`.idea/encodings.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="Encoding" defaultCharsetForPropertiesFiles="UTF-8">
    <!-- 设置 - 编辑器 - 文件编码 - 项目编码 -->
    <file url="PROJECT" charset="UTF-8" />
  </component>
</project>
```

类注释默认自带 `@since`: `.idea/fileTemplates/internal/*.java`
