<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>CPU</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
                <tr>
                  <th class="is-leaf"><div class="cell">attribute</div></th>
                  <th class="is-leaf"><div class="cell">value</div></th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="cell">The core number</div></td>
                  <td><div class="cell" v-if="server.cpu">{{ server.cpu.cpuNum }}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">User usage</div></td>
                  <td><div class="cell" v-if="server.cpu">{{ server.cpu.used }}%</div></td>
                </tr>
                <tr>
                  <td><div class="cell">System usage</div></td>
                  <td><div class="cell" v-if="server.cpu">{{ server.cpu.sys }}%</div></td>
                </tr>
                <tr>
                  <td><div class="cell">Current idle rate</div></td>
                  <td><div class="cell" v-if="server.cpu">{{ server.cpu.free }}%</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>

      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>memory</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
                <tr>
                  <th class="is-leaf"><div class="cell">attribute</div></th>
                  <th class="is-leaf"><div class="cell">memory</div></th>
                  <th class="is-leaf"><div class="cell">JVM</div></th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="cell">The total memory</div></td>
                  <td><div class="cell" v-if="server.mem">{{ server.mem.total }}G</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.total }}M</div></td>
                </tr>
                <tr>
                  <td><div class="cell">Have used the memory</div></td>
                  <td><div class="cell" v-if="server.mem">{{ server.mem.used}}G</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.used}}M</div></td>
                </tr>
                <tr>
                  <td><div class="cell">The remaining memory</div></td>
                  <td><div class="cell" v-if="server.mem">{{ server.mem.free }}G</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.free }}M</div></td>
                </tr>
                <tr>
                  <td><div class="cell">usage</div></td>
                  <td><div class="cell" v-if="server.mem" :class="{'text-danger': server.mem.usage > 80}">{{ server.mem.usage }}%</div></td>
                  <td><div class="cell" v-if="server.jvm" :class="{'text-danger': server.jvm.usage > 80}">{{ server.jvm.usage }}%</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>

      <el-col :span="24" class="card-box">
        <el-card>
          <div slot="header">
            <span>Server Information</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <tbody>
                <tr>
                  <td><div class="cell">Server name</div></td>
                  <td><div class="cell" v-if="server.sys">{{ server.sys.computerName }}</div></td>
                  <td><div class="cell">The operating system</div></td>
                  <td><div class="cell" v-if="server.sys">{{ server.sys.osName }}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">The serverIP</div></td>
                  <td><div class="cell" v-if="server.sys">{{ server.sys.computerIp }}</div></td>
                  <td><div class="cell">System architecture</div></td>
                  <td><div class="cell" v-if="server.sys">{{ server.sys.osArch }}</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>

      <el-col :span="24" class="card-box">
        <el-card>
          <div slot="header">
            <span>JavaVm Information</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <tbody>
                <tr>
                  <td><div class="cell">JavaThe name of the</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.name }}</div></td>
                  <td><div class="cell">Javaversion</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.version }}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">The startup time</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.startTime }}</div></td>
                  <td><div class="cell">The running time</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.runTime }}</div></td>
                </tr>
                <tr>
                  <td colspan="1"><div class="cell">The installation path</div></td>
                  <td colspan="3"><div class="cell" v-if="server.jvm">{{ server.jvm.home }}</div></td>
                </tr>
                <tr>
                  <td colspan="1"><div class="cell">Project path</div></td>
                  <td colspan="3"><div class="cell" v-if="server.sys">{{ server.sys.userDir }}</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>

      <el-col :span="24" class="card-box">
        <el-card>
          <div slot="header">
            <span>Disk status</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
                <tr>
                  <th class="is-leaf"><div class="cell">Drive path</div></th>
                  <th class="is-leaf"><div class="cell">The file system</div></th>
                  <th class="is-leaf"><div class="cell">Drive type</div></th>
                  <th class="is-leaf"><div class="cell">Total size</div></th>
                  <th class="is-leaf"><div class="cell">Available size</div></th>
                  <th class="is-leaf"><div class="cell">Have used the size</div></th>
                  <th class="is-leaf"><div class="cell">Percentage used</div></th>
                </tr>
              </thead>
              <tbody v-if="server.sysFiles">
                <tr v-for="sysFile in server.sysFiles">
                  <td><div class="cell">{{ sysFile.dirName }}</div></td>
                  <td><div class="cell">{{ sysFile.sysTypeName }}</div></td>
                  <td><div class="cell">{{ sysFile.typeName }}</div></td>
                  <td><div class="cell">{{ sysFile.total }}</div></td>
                  <td><div class="cell">{{ sysFile.free }}</div></td>
                  <td><div class="cell">{{ sysFile.used }}</div></td>
                  <td><div class="cell" :class="{'text-danger': sysFile.usage > 80}">{{ sysFile.usage }}%</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getServer } from "@/api/monitor/server";

export default {
  name: "Server",
  data() {
    return {
      // Load layer information
      loading: [],
      // Server Information
      server: []
    };
  },
  created() {
    this.getList();
    this.openLoading();
  },
  methods: {
    /** Querying Server Information */
    getList() {
      getServer().then(response => {
        this.server = response.data;
        this.loading.close();
      });
    },
    // Open the loading layer
    openLoading() {
      this.loading = this.$loading({
        lock: true,
        text: "Frantically reading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
    }
  }
};
</script>