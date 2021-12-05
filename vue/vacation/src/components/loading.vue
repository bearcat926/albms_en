<template>
  <div class="markbox" v-show="loading" style="background-color: rgba(0, 0, 0, 0.5);">
    <div class="sun-loading"></div>
  </div>
</template>

<script>
export default {
    name: 'loading',
    data() {
        return {
            loading: false
        }
    },
    created() {
        var that = this;
        this.bus.$on('loading', function(data) {
            that.loading = !!data;
        })
    }
}
</script>

<style>
.sun-loading {
	width: 45px;
	height: 45px;
	display: block;
	animation: sunLoading 1s steps(12, end) infinite;
	background: transparent 	url('../assets/image/login_top.png');
	background-size: 100%;
    margin:auto;
    position:absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right:0;
}
</style>
