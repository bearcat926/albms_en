<template>
  <div class="aui-padded-15">
    <div class="map">
      <el-input
        v-model="addressKeyword"
        placeholder="Please enter the address to find the relevant location"
        clearable
        style="margin-bottom: 20px"
      >
        <el-button
          slot="append"
          icon="el-icon-search"
          @click="getAddressKeyword"
        ></el-button>
      </el-input>
      <div id="container" style="width: 100%; height: 500px"></div>
      <div class="aui-padded-t-15 aui-padded-b-15 flex flex_start">
        <div>
          <span>longitude:</span>
          <input class="aui-padded-5" type="text" v-model="shopInfo.lng" />
        </div>
        <div class="aui-padded-l-15">
          <span>latitude:</span>
          <input class="aui-padded-5" type="text" v-model="shopInfo.lat" />
        </div>

        <!-- <span class="aui-padded-l-15">latitude:{{shopInfo.lat}}</span> -->
      </div>
    </div>
  </div>
</template>

<script>
import maps from "qqmap";
export default {
  name: "qqmap",
  data() {
    return {
      //Tencent map
      map: null,
      getAddress: null,
      getAddCode: null,
      addressKeyword: "",
      lat: "34.230684",
      lon: "108.886903",
      shopInfo: {
        lng: "",
        lat: "",
      },
    };
  },
  watch: {
    showModal: function (newValue) {
      if (newValue) {
        this.init();
      }
    },
  },
  created() {
    this.init();
  },

  methods: {
    //Initialize the map
    init() {
      var that = this;
      maps.init("WW5BZ-LCN3U-VLPVA-4KQIT-PO5RO-ZVBOK", () => {
        var myLatlng = new qq.maps.LatLng(that.lat, that.lon);
        var myOptions = {
          zoom: 16,
          center: myLatlng,
          mapTypeId: qq.maps.MapTypeId.ROADMAP,
        };
        that.map = new qq.maps.Map(
          document.getElementById("container"),
          myOptions
        );
        //Gets the clicked address
        qq.maps.event.addListener(that.map, "click", function (event) {
          // Gets the map coordinates after the click
          that.shopInfo.lng = event.latLng.getLng();
          that.shopInfo.lat = event.latLng.getLat();
          console.log(that.shopInfo);
          that.getAddressCode();
        });
        //The address call displays the map location and sets the address
        that.getAddress = new qq.maps.Geocoder({
          complete: function (result) {
            that.map.setCenter(result.detail.location);
            console.log(result.detail.location);
            that.shopInfo.lng = result.detail.location.lng;
            that.shopInfo.lat = result.detail.location.lat;
            var marker = new qq.maps.Marker({
              map: that.map,
              position: result.detail.location,
            });
          },
        });
        //Display map address by coordinates
        that.getAddCode = new qq.maps.Geocoder({
          complete: function (result) {
            that.addressKeyword = result.detail.address;
          },
        });
      });
    },

    //Get location by address
    getAddressKeyword() {
      console.log(this.getAddress);
      //throughgetLocation();Method to obtain the location information value
      this.getAddress.getLocation(this.addressKeyword); //Call the native interface
    },
    // Get the address from coordinates
    getAddressCode() {
      var lat = parseFloat(this.shopInfo.lat);
      var lng = parseFloat(this.shopInfo.lng);
      var latLng = new qq.maps.LatLng(lat, lng);
      //Call the get location method
      this.getAddCode.getAddress(latLng);
    },
  },
};
</script>

<style >
</style>