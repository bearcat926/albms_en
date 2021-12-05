<template>
  <div :id="id" :class="className" :style="{height:height,width:width}" :data="data"/>
</template>

<script>
import echarts from "echarts";
import resize from "@/components/Charts/mixins/resize";

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "pieChart",
    },
    id: {
      type: String,
      default: "pieChart",
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
  data() {
    return {
      chart: null,
      data_num: "",
    };
  },
  watch: {
    data(newVal, oldVal) {
      if (newVal != "") {
        var sum = 0;
        for (let i = 0; i < this.data.length; i++) {
          sum += this.data[i].value;
        }
        this.data_num = sum;
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
      var data = this.data;

      this.chart = echarts.init(document.getElementById(this.id));
      this.chart.setOption({
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)",
        },
        legend: {
          icon: "circle",
          orient: "vertical",
          left: 140,
          bottom: 20,
          selectedMode: false, //Cancel the click event on the legend
          itemWidth: 9,
          itemHeight: 9,
          // formatter: function (name) {
          //   var total = 0;
          //   var target;
          //   for (var i = 0, l = data.length; i < l; i++) {
          //     total += data[i].value;
          //     if (data[i].name == name) {
          //       target = data[i].ratio;
          //     }
          //   }
          //   return (
          //     name + "\t\t\t" + target + "Comparison with last month"
          //   );
          // },
        },

        legend: {
          icon: "circle",
          orient: "vertical",
          left: 40,
          bottom: 120,
          selectedMode: false, //Cancel the click event on the legend
          itemWidth: 9,
          itemHeight: 9,
          data:["frame", "As a mirror", "For goods", "Free goods"],
        },

        //Color ring
        color: ["#4860FB", "#5ECC59", "#745BEB", "#E8522A"],
        
        // Sets the data in the middle of the ring
        graphic: [
          {
            type: "text",
            left: "58%",
            top: "44%",
            z: 10,
            style: {
              fill: "#717880",
              text: this.data_num + "\nThe total number of goods",
              font: "16px Microsoft YaHei",
              textAlign: "middle",
              textVerticalAlign: "middle",
            },
          },
        ],
        series: [
          {
            hoverAnimation: false,
            name: "Commodity proportion analysis",
            type: "pie",
             radius: ["35%", "48%"], //Two representation rings:Within a radius of,Outer radius
            center: ["65%", "48%"], //Or so,Up and down
            avoidLabelOverlap: true,
            label: {
              normal: {
                show: false,
                position: "outer",
                //The template variables have {a}, {b},{c},{d},{e},Respectively indicate the series names,The data of,The data values etc.。
                formatter: "{c_set|{c}\t\t}people" + "\n\nAccounted for{d}%",
                borderWidth: 20,
                borderRadius: 4,
                padding: [90, -50],
                rich: {
                  a_set: {
                    color: "#1a1a1a",
                    lineHeight: 20,
                    align: "center",
                  },
                  // c_set: {
                  //   color: "#808080",
                  // },
                },
              },
              // emphasis: {
              //   show: false,
              //   textStyle: {
              //     fontSize: "12",
              //   },
              // },
            },
            //Color of traction line
            // labelLine: {
            //   normal: {
            //     show: false,
            //     length: 30,
            //     length2: 50,
            //   },
            // },
            data: this.data,
          },
        ],
      });
    },
  },
};
</script>
