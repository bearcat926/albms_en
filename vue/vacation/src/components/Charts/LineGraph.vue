<template>
  <div :id="id" :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from "echarts";
import resize from "./mixins/resize";

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "LineGraph",
    },
    id: {
      type: String,
      default: "LineGraph",
    },
    width: {
      type: String,
      default: "200px",
    },
    height: {
      type: String,
      default: "200px",
    },
  },
  data() {
    return {
      chart: null,
    };
  },
  mounted() {
    this.initChart();
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
        xAxis: {
          type: "category",
          boundaryGap: false,
          axisLine: {
            lineStyle: {
              // Set up thexAxis color
              color: '#999',
            }
          },
        },
        yAxis: {
          type: "value",
          boundaryGap: [0, "30%"],
           axisLine: {
            lineStyle: {
              // Set up thexAxis color
              color: '#999',
            }
          },
        },
        visualMap: {
          type: "piecewise",
          show: false,
          dimension: 0,
          seriesIndex: 0,
        
        },
        series: [
          {
            type: "line",
            smooth: false,
            symbol: "circle",
            symbolSize: 10,
            itemStyle: {
              normal: {
                color: "#999",
                borderColor:'#5962ff',  //Inflection point border color
                
              },
            },
            lineStyle: {
                  color: "#5962ff",
                  width: 1,
                },
            
            markLine: {
              symbol: ["none", "none"],
              label: { show: false },
              data: [
                { xAxis: 1 },
                { xAxis: 2 },
                { xAxis: 3 },
                { xAxis: 4 },
                { xAxis: 5 },
                { xAxis: 6 },
                { xAxis: 7 },
                { xAxis: 8 },
                { xAxis: 9 },
                { xAxis: 10 },
                { xAxis: 11 },
                { xAxis: 12 },
              ],
            },
            areaStyle: {
              color: "none",
            },
            data: [
              ["1month", 200],
              ["2month", 400],
              ["3month", 650],
              ["4month", 500],
              ["5month", 250],
              ["6month", 300],
              ["7month", 450],
              ["8month", 300],
              ["9month", 100],
              ["10month", 300],
              ["11month", 200],
              ["12month", 100],
            ],
          },
        ],
      });
    },
  },
};
</script>
