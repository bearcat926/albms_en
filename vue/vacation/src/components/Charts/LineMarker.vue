<template>
  <div :id="id" :class="className" :style="{height:height,width:width}" :data="data" />
</template>

<script>
import echarts from "echarts";
import resize from "./mixins/resize";

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart",
    },
    id: {
      type: String,
      default: "chart",
    },
    width: {
      type: String,
      default: "200px",
    },
    height: {
      type: String,
      default: "200px",
    },
    data: {},
  },

  watch: {
    data(newVal, oldVal) {
      if (newVal != "") {
        this.initChart();
      }
    },
     deep: true
  },
  data() {
    return {
      chart: null,
    };
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
        backgroundColor: "#fff",
        title: {
          top: 20,
          text: "",
          textStyle: {
            fontWeight: "normal",
            fontSize: 30,
            color: "#222",
          },
          left: "1%",
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            lineStyle: {
              color: "#57617B",
            },
          },
        },

        grid: {
          top: 100,
          left: "2%",
          right: "3%",
          top: "20%",
          bottom: "8%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            axisLine: {
              lineStyle: {
                color: "#999",
              },
            },
            data: this.data.monthArray,
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "yuan",
            axisTick: {
              show: false,
            },
            axisLine: {
              lineStyle: {
                color: "#999",
              },
            },
            axisLabel: {
              margin: 10,
              textStyle: {
                fontSize: 14,
              },
            },
            splitLine: {
              lineStyle: {
                color: "#eee",
              },
            },
          },
        ],
        series: [
          {
            name: "sales",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 4,
              },
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: "rgba(72, 96, 251, 0.8)",
                    },
                    {
                      offset: 0.8,
                      color: "rgba(72, 96, 251, 0.05)",
                    },
                  ],
                  false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)",
                shadowBlur: 10,
              },
            },
            itemStyle: {
              normal: {
                color: "rgb(72, 96, 251)",
                borderColor: "rgba(72, 96, 251,0.2)",
                borderWidth: 12,
              },
            },
            data: this.data.salePriceArray,
          },
        ],
      });
    },
  },
};
</script>
