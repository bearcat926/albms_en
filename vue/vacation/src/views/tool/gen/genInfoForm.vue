<template>
  <el-form ref="genInfoForm" :model="info" :rules="rules" label-width="150px">
    <el-row>
      <el-col :span="12">
        <el-form-item prop="tplCategory">
          <span slot="label">Generate template</span>
          <el-select v-model="info.tplCategory">
            <el-option label="Single table(Add and delete)" value="crud" />
            <el-option label="Tree table(Add and delete)" value="tree"/>
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item prop="packageName">
          <span slot="label">
            Generate package path
            <el-tooltip content="Which one is generatedjavaUnder the bag,For example, com.ruoyi.system" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.packageName" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item prop="moduleName">
          <span slot="label">
            Generate module name
            <el-tooltip content="Can be understood as the subsystem name,For example, system" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.moduleName" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item prop="businessName">
          <span slot="label">
            Generate a business name
            <el-tooltip content="It can be understood as the English name of the function,For example, user" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.businessName" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item prop="functionName">
          <span slot="label">
            Generate function name
            <el-tooltip content="Used as a class description,For example, The user" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.functionName" />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row v-show="info.tplCategory == 'tree'">
      <h4 class="form-header">Other information</h4>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            Tree coded field
            <el-tooltip content="Tree displays the encoded field name, Such as:dept_id" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.treeCode" placeholder="Please select a">
            <el-option
              v-for="column in info.columns"
              :key="column.columnName"
              :label="column.columnName + ':' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            Tree parent code field
            <el-tooltip content="Tree displays the name of the parent encoded field, Such as:parent_Id" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.treeParentCode" placeholder="Please select a">
            <el-option
              v-for="column in info.columns"
              :key="column.columnName"
              :label="column.columnName + ':' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            Tree name field
            <el-tooltip content="Display name field name of the tree node, Such as:dept_name" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.treeName" placeholder="Please select a">
            <el-option
              v-for="column in info.columns"
              :key="column.columnName"
              :label="column.columnName + ':' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script>
export default {
  name: "BasicInfoForm",
  props: {
    info: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      rules: {
        tplCategory: [
          { required: true, message: "Select generate template", trigger: "blur" }
        ],
        packageName: [
          { required: true, message: "Please enter the generated package path", trigger: "blur" }
        ],
        moduleName: [
          { required: true, message: "Please enter the build module name", trigger: "blur" }
        ],
        businessName: [
          { required: true, message: "Please enter a generate business name", trigger: "blur" }
        ],
        functionName: [
          { required: true, message: "Please enter the build feature name", trigger: "blur" }
        ]
      }
    };
  },
  created() {}
};
</script>
