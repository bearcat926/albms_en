<template>
  <div>
    <mu-circular-progress :size="40" v-if="loading"/>
    <div v-html="html"></div>
  </div>
</template>
<style>

</style>
<script>
import Vue from 'vue'
import MuseUI from 'muse-ui'
import 'muse-ui/dist/muse-ui.css'
import VueResource from 'vue-resource'

Vue.use(MuseUI)
Vue.use(VueResource);

export default{
  // Please use when using :url.sync=""The value of
  props: {
    url: {
      required: true
    }
  },
  data () {
    return {
      loading: false,
      html: ''
    }
  },
  watch: {
    url (value) {
      this.load(value)
    }
  },
  mounted () {
    this.load(this.url)
  },
  methods: {
    load (url) {
      if (url && url.length > 0) {
        // In the load
        this.loading = true
        let param = {
          accept: 'text/html, text/plain'
        }
        this.$http.get(url, param).then((response) => {
          this.loading = false
          // To deal withHTMLAccording to
          this.html = response.data
        }).catch(() => {
          this.loading = false
          this.html = 'Load failed'
        })
      }
    }
  }
}
</script>
