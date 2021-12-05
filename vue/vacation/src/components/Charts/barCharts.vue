<template>
  <div :class="className" :style="{height:height,width:width}" :data="data"/>
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";

const animationDuration = 6000;

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "300px",
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
  mounted() {
    // this.$nextTick(() => {
    //   this.initChart();
    // });
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
      this.chart = echarts.init(this.$el, "macarons");

      this.chart.setOption({
        legend: {
          type: "plain", // Figure column type,The default is 'plain'
          top: "0%", // The position of the diagram column relative to the container top\bottom\left\right
          selected: {
            // 'sales': true  // Figure column selection,The graph loads to show the selected graph column,The default istrue
          },
          textStyle: {
            // Diagram column content style
            color: "#0D1444", // The font color
          },
          tooltip: {
            // Figure column prompt box,Not displayed by default
            show: true,
            color: "red",
          },
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // Axis indicator,Axis trigger works
            type: "shadow", // Default is a straight line,Optional for:'line' | 'shadow'
          },
        },
        grid: {
          top: 10,
          left: "2%",
          right: "2%",
          top:"15%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            data: this.data.companyArry,
            axisTick: {
              alignWithLabel: true,
            },
            axisLine: {
              lineStyle: {
                type: "solid",
                color: "#eee", //The color of the left line
                width: "1", //The width of the coordinate line
              },
            },
            axisLabel: {
              show: true,
                // interval:3,
                rotate:30,
              textStyle: {
                color: "#0D1444", //Coordinates are worth specific colors
              },
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            axisTick: {
              show: false,
            },
            axisLine: {
              lineStyle: {
                type: "solid",
                color: "#eee", //The color of the left line
                width: "1", //The width of the coordinate line
              },
            },
            axisLabel: {
              
              textStyle: {
                color: "#0D1444", //Coordinates are worth specific colors
              },
            },
          },
        ],
        series: [
          {
            name: "Number of stores meeting performance standards",
            type: "bar",
            stack: "vistors",
            barWidth: "50%",
            data: this.data.shopReachArry,
            animationDuration,
            color: ["#4860FB"],
          },
          {
            name: "Number of stores",
            type: "bar",
            stack: "vistors",
            barWidth: "50%",
            data: this.data.shopArry,
            animationDuration,
            color: ["#E4E6F6"],
          },
        ],
      });
    },
  },
};
</script>
