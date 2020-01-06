<template>
  <section>
    <el-form
      size="medium"
      :model="dataForm"
      :rules="rules"
      ref="dataForm"
      label-width="180px"
      class="page-form"
    >
      <el-form-item label="点击地图获取坐标" prop="coordinate">
        <el-input
          type="text"
          placeholder="输入省或市"
          readonly
          v-model="dataForm.coordinate"
          style="width:90%;"
        ></el-input>
      </el-form-item>
      <el-form-item label="" prop>
        <div id="map-container" style="width:100%;height:400px;"></div>
      </el-form-item>
      <el-form-item>
        <el-button @click="$router.go(-1)">返 回</el-button>
        <!-- <el-button type="primary" @click="handleSave('dataForm')">保 存</el-button> -->
        <reqButton @handleReq = "handleSave('dataForm')" />
      </el-form-item>
    </el-form>
  </section>
</template>
<script>
import { QQmap } from "@/utils/qqmap";
export default {
  data() {
    return {
      latitude: 36.67495,
      longitude: 117.02636,
      dataForm: {
        coordinate: ""
      },
      rules: {
        coordinate: [
          { required: true, message: "请在地图上点击选点", trigger: "blur" }
        ]
      }
    };
  },
  created() {},
  mounted() {
    /*eslint-disable */
    QQmap().then(qq => {
      var lat = this.$route.params.lat != 'null' ? this.$route.params.lat : this.latitude
      var lng = this.$route.params.lng != 'null' ? this.$route.params.lng : this.longitude
      this.dataForm.coordinate =lat + "," + lng;

      var map = new qq.maps.Map(document.getElementById("map-container"), {
        center: new qq.maps.LatLng(lat, lng),
        zoom: 14
      });
      qq.maps.event.addListener(map, "click", event => {
        this.dataForm.coordinate =
          event.latLng.getLat() + "," + event.latLng.getLng();
      });

      var marker = new qq.maps.Marker({
        position: new qq.maps.LatLng( lat, lng),
        map: map
      });

      var anchor = new qq.maps.Point(0, 39),
        size = new qq.maps.Size(42, 68),
        origin = new qq.maps.Point(0, 0),
        markerIcon = new qq.maps.MarkerImage(
          "https://3gimg.qq.com/lightmap/api_v2/2/4/99/theme/default/imgs/marker.png",
          size,
          origin,
          anchor
        );
      marker.setIcon(markerIcon);

      //添加监听事件   获取鼠标单击事件
      qq.maps.event.addListener(map, "click", function(event) {
        var marker = new qq.maps.Marker({
          position: event.latLng,
          map: map
        });
        qq.maps.event.addListener(map, "click", function(event) {
          marker.setMap(null);
        });
      });

    });
  },
  methods: {
    updateCoordinate() {
      let id = this.$route.params.id;
      let coordinate = this.dataForm.coordinate;
      this.$http
        .post("org/divisioncoordinate", { id, coordinate })
        .then(res => {
          if (res.status == "success") {
            this.$message({ message: res.msg, type: "success" });
            this.$router.go(-1);
          }
        });
    },
    handleSave(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.updateCoordinate();
        } else {
          this.$message.error("提交失败!");
          return false;
        }
      });
    }
  }
};
</script>