<template>
  <el-dialog title="设置坐标" :visible.sync="visibleDialog" width="700px" :before-close="handleClose">
    <el-form size="medium" label-width="150px" :model="dataForm" :rules="rules" ref="dataForm">
      <el-form-item label="点击地图获取坐标" prop="coordinate">
        <el-input
          type="text"
          placeholder="输入省或市"
          readonly
          v-model="dataForm.coordinate"
          style="width:90%;"
        ></el-input>
      </el-form-item>
      <div id="map-container" style="width:100%;height:400px;"></div>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <!-- <el-button type="primary" @click="handleSave('dataForm')">保 存</el-button> -->
        <reqButton @handleReq = "handleSave('dataForm')"/>
    </div>
  </el-dialog>
</template>
<script>
import { QQmap } from "@/utils/qqmap";
export default {
  props: {
    visibleDialog: { required: true, default: false },
    config: { required: false, default: {} },
    latitude: { required: false, default: 36.67495 }, // 纬度
    longitude: { required: false, default: 117.02636 } // 经度
  },
  data() {
    return {
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
    QQmap().then(qq => {
      var map = new qq.maps.Map(document.getElementById("map-container"), {
        center: new qq.maps.LatLng(this.latitude, this.longitude),
        zoom: 11
      });
      qq.maps.event.addListener(map, "click", event => {
        this.dataForm.coordinate =
          event.latLng.getLat() + "," + event.latLng.getLng();
      });
    });
  },
  methods: {
    handleClose() {
      this.$emit("update:visibleDialog", false);
    },
    handleSave(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$emit("getCoordinate", this.config, this.dataForm.coordinate);
          this.handleClose();
        } else {
          this.$message.error("提交失败!");
          return false;
        }
      });
    }
  }
};
</script>