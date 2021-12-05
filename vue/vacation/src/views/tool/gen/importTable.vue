<template>
  <!-- The import table -->
  <el-dialog title="The import table" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="The name of the table" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="Please enter a table name"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Table describes" prop="tableComment">
        <el-input
          v-model="queryParams.tableComment"
          placeholder="Please enter a table description"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">reset</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="dbTableList" @selection-change="handleSelectionChange" height="260px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="tableName" label="The name of the table"></el-table-column>
        <el-table-column prop="tableComment" label="Table describes"></el-table-column>
        <el-table-column prop="createTime" label="Creation time"></el-table-column>
        <el-table-column prop="updateTime" label="Update time"></el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleImportTable">indeed set</el-button>
      <el-button @click="visible = false">take eliminate</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listDbTable, importTable } from "@/api/tool/gen";
export default {
  data() {
    return {
      // The mask layer
      visible: false,
      // Select the array value
      tables: [],
      // The total number of article
      total: 0,
      // Table data
      dbTableList: [],
      // Query parameters
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: undefined,
        tableComment: undefined
      }
    };
  },
  methods: {
    // Show bounced
    show() {
      this.getList();
      this.visible = true;
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // Select data in multiple boxes
    handleSelectionChange(selection) {
      this.tables = selection.map(item => item.tableName);
    },
    // Query table data
    getList() {
      listDbTable(this.queryParams).then(res => {
        if (res.code === 200) {
          this.dbTableList = res.rows;
          this.total = res.total;
        }
      });
    },
    /** Search button operation */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** Reset button operation */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** Import button operation */
    handleImportTable() {
      importTable({ tables: this.tables.join(",") }).then(res => {
        this.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.$emit("ok");
        }
      });
    }
  }
};
</script>
