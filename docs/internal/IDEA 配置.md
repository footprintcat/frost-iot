# IDEA 配置

## 需要手动配置的配置项

> 以下配置未提交到代码仓库中，需手动进行配置

### ⛓️ 动态添加明确的 import

该配置项未保存在 .idea 目录中

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

### ⛓️ 动态优化 import

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

### ⛓️ 使用项目代码样式

`.idea/codeStyles/codeStyleConfig.xml`

```xml
<component name="ProjectCodeStyleConfiguration">
  <state>
    <!-- 设置 - 编辑器 - 代码样式 - 方案：项目 -->
    <option name="USE_PER_PROJECT_SETTINGS" value="true" />
  </state>
</component>
```

### ⛓️ 代码样式相关配置

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

### ⛓️ 拼写检查白名单

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
      <!-- gradle.properties 文件中 JVM 参数 -->
      <w>jvmargs</w>
      <!-- PostgreSQL 数据库 -->
      <w>postgre</w>
      <!-- application.yml 中 rapidoc 配置 -->
      <w>rapidoc</w>
    </words>
  </dictionary>
</component>
```

### ⛓️ 项目编码

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

## 文件头版权配置

### ⛓️ 文件头版权

#### 配置作用域

1. 设置 - 外观与行为 - 作用域

2. 添加作用域 - 共享

   ```
   名称：项目 Java 代码 (用于添加版权头)
   模式：file[frost-iot*]:*.java&&!test:*..*
   ```

#### 配置版权文本

1. 设置 - 编辑器 - 版权 - 版权配置文件 - 创建 1 个配置文件

   ```
   名称：frost-iot 寒霜物联 项目许可证
   ```

2. 配置版权文本

   ```
   Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)

   This source code is licensed under the BSD-3-Clause license found in the
   LICENSE file in the root directory of this source tree.

   SPDX-License-Identifier: BSD-3-Clause
   ```

3. 勾选 通过 VCS 共享

#### 配置默认项目版权

1. 设置 - 编辑器 - 版权 - 默认项目版权
2. 下拉框选择 `frost-iot 寒霜物联 项目许可证`
3. 点击添加
4. 作用域选择：`项目 Java 代码 (用于添加版权头)`，版权选择 `frost-iot 寒霜物联 项目许可证`

配置完成，部分配置需要等 IDEA 关闭时才会保存

### 📄 相关配置文件

相关配置文件所在目录：`.idea/copyright`, `.idea/scopes`

`.idea/scopes/_Java____.xml`

```xml
<component name="DependencyValidationManager">
  <scope name="项目 Java 代码 (用于添加版权头)" pattern="file[frost-iot*]:*.java&amp;&amp;!test:*..*" />
</component>
```

`.idea/copyright/frost_iot__.xml`

```xml
<component name="CopyrightManager">
  <copyright>
    <option name="notice" value="Copyright (c) 武汉脚印猫科技有限公司 (Wuhan Footprint Cat Technology Co., Ltd.)&#10;&#10;This source code is licensed under the BSD-3-Clause license found in the&#10;LICENSE file in the root directory of this source tree.&#10;&#10;SPDX-License-Identifier: BSD-3-Clause" />
    <option name="myName" value="frost-iot 寒霜物联 项目许可证" />
  </copyright>
</component>
```

`.idea/copyright/profiles_settings.xml`

```xml
<component name="CopyrightManager">
  <settings default="frost-iot 寒霜物联 项目许可证">
    <module2copyright>
      <element module="项目 Java 代码 (用于添加版权头)" copyright="frost-iot 寒霜物联 项目许可证" />
    </module2copyright>
    <LanguageOptions name="JAVA" />
  </settings>
</component>
```

#### 使用方式

左侧文件树中选择顶级目录

1. IDEA 顶部菜单 - 代码 - 更新版权
2. 选择 更新版权 作用域: 自定义作用域: `项目 Java 代码 (用于添加版权头)`
3. 点击 [分析] 按钮
