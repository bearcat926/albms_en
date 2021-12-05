<template>
  <div :id="id" :class="className" :style="{height:height,width:width}" :data="data"/>
</template>

<script>
import echarts from 'echarts'
import resize from '@/components/Charts/mixins/resize'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'pieChart'
    },
    id: {
      type: String,
      default: 'memberPieChart'
    },
    width: {
      type: String,
      default: '200px'
    },
    height: {
      type: String,
      default: '200px'
    },
    clientStatus: {
      type: Number,
      required: true
    },
    data: {}
  },
  data() {
    return {
      chart: null,
      data_num: '6.5wan'
    }
  },
  watch: {
    data(newVal, oldVal) {
      if (newVal != '') {
        this.initChart()
      }
    },
    deep: true
  },
  mounted() {
  },

  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      let clientTitle = this.clientStatus === 0 ? 'Associate member' : 'members'

      this.chart = echarts.init(document.getElementById(this.id))
      this.chart.setOption({
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          icon: 'circle',
          orient: 'vertical',
          left: 20,
          top: 50,
          selectedMode: false, //Cancel the click event on the legend
          itemWidth: 9,
          itemHeight: 9
          // formatter: function (name) {
          //   var total = 0;
          //   var target;
          //   for (var i = 0, l = data.length; i < l; i++) {
          //     total += data[i].value;
          //     if (data[i].name == name) {
          //       target = data[i].value;
          //     }
          //   }
          //   return (
          //     name + "\t\t\t" + target
          //   );
          // },
        },
        //Color ring
        color: ['#4860FB', '#E8522A', '#01ff79', '#745BEB', '#ffa200', '#ff0000', '#ad9ae9', '#ee83ab', '#47d1ea', '#fcff00', '#05f52c'],
        series: [
          {
            hoverAnimation: false,
            name: clientTitle + 'source',
            type: 'pie',
            radius: ['40%', '60%'], //Two representation rings:Within a radius of,Outer radius
            center: ['70%', '53%'], //Or so,Up and down
            avoidLabelOverlap: true,
            label: {
              normal: {
                show: false
              }

            },

            // //Color of traction line
            labelLine: {
              normal: {
                show: false
              }
            },
            data: this.data
          }
        ]
      })
    }
  }
}
</script>
