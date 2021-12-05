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
          left: 150,
          top: 270,
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

        //Color ring
        color: [
          "#F77317",
          "#4860FB",
          "#5ECC59",
          "#745BEB",
          "#FFE019",
          "#E8522A",
          "#FF6919",
          "#3ef114",
          "#f11448",
          "#14caf1",
          "#da14f1",
        ],
        // Sets the data in the middle of the ring
        graphic: [
          {
            type: "text",
            left: "43%",
            top: "31%",
            z: 10,
            style: {
              fill: "#717880",
              text: this.data_num + "\nTotal number of users",
              font: "14px Microsoft YaHei",
              textAlign: "middle",
              textVerticalAlign: "middle",
            },
          },
        ],
        series: [
          {
            hoverAnimation: false,
            name: "Member analysis",
            type: "pie",
            radius: ["32%", "45%"], //Two representation rings:Within a radius of,Outer radius
            center: ["50%", "35%"], //Or so,Up and down
            avoidLabelOverlap: true,
            label: {
              normal: {
                show: true,
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
              emphasis: {
                show: true,
                textStyle: {
                  fontSize: "12",
                },
              },
            },
            //Color of traction line
            labelLine: {
              normal: {
                show: true,
                length: 30,
                length2: 50,
              },
            },
            data: this.data,
          },
        ],
      });
    },
  },
};
</script>
