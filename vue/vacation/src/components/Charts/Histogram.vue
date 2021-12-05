<template>
  <div :id="id" :class="className" :style="{height:height,width:width}" :data="data"/>
</template>

<script>
import echarts from "echarts";
import resize from "./mixins/resize";

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "Histogram",
    },
    id: {
      type: String,
      default: "Histogram",
    },
    width: {
      type: String,
      default: "200px",
    },
    height: {
      type: String,
      default: "200px",
    },
    data:{}
  },
  data() {
    return {
      chart: null,
      
    };
  },
  watch: {
    data(newVal, oldVal) {
      if (newVal != "") {
        this.initChart();
      }
    },
     deep: true,
  },

  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(document.getElementById(this.id));
      this.chart.setOption({
        legend: {
          x: 30,
          data: [
            // Figure column content
            {
              name: "reservations",
              icon: "rect",
              textStyle: {
                color: "#95a0c4", // Sets the color of a graph column individually
                backgroundColor: "#fff", // Sets the font background color of a column individually
              },
            },
            {
              name: "Sales list",
              icon: "rect",
              textStyle: {
                color: "#95a0c4", // Sets the color of a graph column individually
                backgroundColor: "#fff", // Sets the font background color of a column individually
              },
            },
          ],
        },
        dataset: {
          dimensions:["product", "reservations", "Sales list"],
          source: this.data
        },
        xAxis: {
          type: "category",
          axisLine: {
            lineStyle: {
              color: "#999",
            },
          },
        },
        yAxis: {
          show: true,
          axisLine: {
            show: true,
            lineStyle: {
              color: "#fff",
            }
          },
          axisLabel : {
                            formatter: '{value}',
                            textStyle: {
                                color: '#666'
                            }
                        }
        },
     
        series: [
          {
            type: "bar",
            barWidth: 18,
            itemStyle: {
              normal: {
                color: "#4860FB",
              },
            },
          },
          {
            type: "bar",
            barWidth: 18,
            itemStyle: {
              normal: {
                color: "#5ECC59",
              },
            },
          },
        ],
      });
    },
  },
};
</script>
