<template>
    <section>
        <div class="line page-line"></div>

        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" size="small" label-width="150px" class="page-form-large" style="max-width: 1000px">
            <el-form-item label="课程名称" prop="name">
                <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-form-item label="所属门店" prop="division">
                <checkbox-division v-model="ruleForm.division"></checkbox-division>
            </el-form-item>
            <el-form-item label="所属分类" prop="cate">
                <select-category v-model="ruleForm.cate" :style="{width:'100%'}" @input="handleCategoryChange"></select-category>
            </el-form-item>
            <el-form-item label="选择属性" prop="" v-if="options.length != 0">
                <span v-for="(opt,index) in options" :key="opt.id" style="margin-right: 6px;">
                    <el-select v-model="ruleForm.properties[index]" placeholder="请选择" v-if="opt.properties.length > 0"
                               style="width: 100px;">
                        <el-option v-for="item in opt.properties" :key="item.id" :label="item.name"
                                   :value="item.id"></el-option>
                    </el-select>
                </span>
            </el-form-item>
            <el-form-item label="课程类型" prop="type">
                <radio-status v-model="ruleForm.type" valueDefault="团体课" statusName="product_type"></radio-status>
                <span class="tip-text" style="float: right"></span>
            </el-form-item>
     
            <el-form-item label="价格方案" prop="specifications">
                <product-specification ref="specificationForm" :values.sync="ruleForm.specifications"/>
            </el-form-item>
            <el-form-item label="报名名额" prop="quantity" >
              <el-input type='number' v-model="ruleForm.quantity"></el-input>
              <span class="tip-text" style="float: right">名额会限制购买人数; 留空表示不限制</span>
            </el-form-item>

            <el-form-item label="添加图片" prop="images">
                <image-draggable-uploader :imageList="ruleForm.images" :limit="5" @upload="handleUploadedImage"/>
                <!--<image-uploader :imageList="ruleForm.images" :limit="5" @upload="handleUploadedImage"></image-uploader>-->
                <span class="tip-text" style="float: right"> 首张为封面 至多上传5张 限制大小是2M 建议尺寸750x400px</span>
            </el-form-item>

            <el-form-item label="报名起始日期" prop="date">
                <el-date-picker v-model="ruleForm.date" type="daterange" value-format="yyyy-MM-dd" range-separator="至"
                                start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                <span class="tip-text" style="float: right">学员只能在该日期范围内报名; 留空表示不限制</span>
            </el-form-item>

            <el-form-item label="课时失效日期" prop="expire">
                <el-date-picker v-model="ruleForm.expire" type="date" value-format="yyyy-MM-dd"></el-date-picker>
                <span class="tip-text" style="float: right">即课时的失效日期; 留空表示不限制</span>
            </el-form-item>
  
            <el-form-item label="一句概述" prop="slogan">
                <el-input type="textarea" v-model="ruleForm.slogan"></el-input>
            </el-form-item>
            <el-form-item label="课程简介" prop="description" class="content">
                <el-input type="textarea" v-model="ruleForm.description"></el-input>
            </el-form-item>
            <el-form-item label="训练效果" prop="purpose" class="content">
                <el-input type="textarea" v-model="ruleForm.purpose"></el-input>
            </el-form-item>
            <el-form-item label="适合人群" prop="target" class="content">
                <el-input type="textarea" v-model="ruleForm.target"></el-input>
            </el-form-item>
            <el-form-item label="FAQ" prop="faq" class="content">
                <el-input type="textarea" v-model="ruleForm.faq"></el-input>
            </el-form-item>
            <el-form-item label="注意事项" prop="attention" class="content">
                <el-input type="textarea" v-model="ruleForm.attention"></el-input>
            </el-form-item>
            <el-form-item label="排序权重" prop="sort" >
              <el-input type='number' v-model="ruleForm.sort" placeholder="数值越大越靠前"></el-input>
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <radio-status v-model="ruleForm.status" valueDefault="上架" statusName="product"></radio-status>
            </el-form-item>
            <el-form-item label="是否推荐" prop="recommend">
                <radio-status v-model="ruleForm.recommend" valueDefault="否" statusName="is_yes"></radio-status>
            </el-form-item>
            <!--<el-form-item label="附赠品">-->
                <!--<product-link-material-select :values="selectMaterials" @getRes="getTotalMaterial"/>-->
            <!--</el-form-item>-->
            <el-form-item>
                <!-- <el-button type="primary" @click="submitForm('ruleForm')"> 提 交</el-button> -->
                <reqButton @handleReq = "submitForm('ruleForm')"/>
            </el-form-item>
        </el-form>
    </section>
</template>
<script>
  // import {unescape} from 'lodash'
  // import Editor from '@/components/Editor'
  // import ToolTip from '@/components/ToolTip'
  import selectCategory from '@/components/SelectCategory'
  import radioStatus from '@/components/RadioStatus'
  import checkboxDivision from '@/components/CheckboxDivision'
  import productSpecification from '@/components/ProductSpecification'
  import imageDraggableUploader from '@/components/ImageDraggableUploader'
  // import ProductLinkMaterialSelect from '@/components/ProductLinkMaterialSelect'
  export default {
    components: {
      // ToolTip,
      // "quillEditor": Editor,
      selectCategory,
      radioStatus,
      checkboxDivision,
      imageDraggableUploader,
      productSpecification,
      // ProductLinkMaterialSelect
    },
    data() {
      var validateSpecForm = (rule, value, callback) => {
        if (value.length === 0) {
          callback(new Error('未设置规格'));
        }
        value.forEach(i => {
          if (i.price === '') {
            callback(new Error('规格价格设置有误!'));
          } else if (i.quantity == false || i.quantity < 1) {
            callback(new Error('规格数量设置有误!'));
          }
        })
        callback()
      };
      return {
        ruleForm: {
          id: '',
          name: '',
          cate: [],
          division: [],
          quantity: '',
          slogan: '',
          description: '',
          purpose: '',
          target: '',
          faq: '',
          attention: '',
          expire: '',
          sort: 0,
          recommend: '否',
          date: [],
          status: '上架',
          images: [],
          properties: [],
          materials: [],
          type: '团体课',
          specifications: []
        },
        // selectMaterials:[],
        rules: {
          name: [{required: true, message: '请输入名称', trigger: 'blur'}],
          type: [{required: true, message: '请选择类型', trigger: 'blur'}],
          cate: [{required: true, message: '请选择分类', trigger: 'blur'}],
          division: [{required: true, message: '请选择门店', trigger: 'blur'}],
          images: [{required: true, message: '请上传图片', trigger: 'blur'}],
          specifications: [{required: true, message: '请设置规格', type: 'array'},
          {validator: validateSpecForm }]
          // price: [{required: true, message: '请输入有效价格', trigger: 'blur'}],
        },
        dialogImageUrl: '',
        dialogImageVisible: false,
        options: [],
      };
    },
    watch: {
      'ruleForm.cate'(val) {
        if (val) this.getProperties(val);
      }
//            'ruleForm.cate':{
//                handler:(val,oldVal)=>{
//                    if(val) this.getProperties(id);
//                },
//                deep:true
//            }
    },
    created() {
      if (this.$route.params.id) this.getData();
    },
    methods: {
      submitForm(formName) {
        var openAlert = () => {
            this.$confirm('是否去给该课程排课?', '提交成功', {
              confirmButtonText: '去排课',
              cancelButtonText: '回列表',
              type: 'success',
              center: true
            }).then(() => {
              this.$router.push('/course/schedule');
            }).catch(() => {
              this.$router.push('/product/list');
            });
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.ruleForm.id) {
              this.$http.post("product/edit", this.ruleForm).then((res) => {
                if (res.status == 'success') {
                  openAlert();
                }
              });
            } else {
              this.$http.post("product/create", this.ruleForm).then((res) => {
                if (res.status == 'success') {
                  openAlert();
                }
              });
            }
          } else {
            this.$message.error('提交失败,请检查输入内容!');
            return false;
          }
        });
      },
      getData() {
        this.$http.fetch('product/find', {"id": this.$route.params.id}).then((res) => {
          this.ruleForm.id = res.data.id;
          this.ruleForm.name = res.data.name;
          this.ruleForm.cate = res.data.category_ids ? res.data.category_ids.split(",").map((val) => {
            return parseInt(val);
          }) : [];
          this.ruleForm.images = res.data.images;
          this.ruleForm.specifications = res.data.specs;
          this.ruleForm.slogan = res.data.slogan;
          this.ruleForm.quantity = res.data.quantity;
          this.ruleForm.description = res.data.description;
          this.ruleForm.purpose = res.data.purpose;
          this.ruleForm.target = res.data.target;
          this.ruleForm.expire = res.data.expired_at;
          this.ruleForm.faq = res.data.faq;
          this.ruleForm.attention = res.data.attention;
          this.ruleForm.sort = res.data.sort;
          this.ruleForm.recommend = res.data.recommend;
          this.ruleForm.date = res.data.start_at ? [res.data.start_at, res.data.end_at] : [];
          this.ruleForm.status = res.data.status;
          this.ruleForm.type = res.data.type;
          // this.ruleForm.price = res.data.price;
          // this.ruleForm.description = unescape(res.data.description);
          // this.ruleForm.price_original = res.data.price_original ;
          // this.selectMaterials = res.data.link_materials;
          var properties = new Array();
          for (var i in res.data.properties) {
            properties[res.data.properties[i].type_id] = res.data.properties[i].id;
          }
          this.ruleForm.properties = properties;

          this.ruleForm.division = res.data.divisions.map((val) => {
            return val.id;
          });

        });
      },
      handleUploadedImage(image) {
        this.ruleForm.images = image;
      },
      // handlePriceInput(value) {
      //   var newVal = value.trim().slice(0, value.indexOf('.') === -1 ? value.length : value.indexOf('.') + 3);
      //   setTimeout(() => {
      //     if (isNaN(newVal)) this.ruleForm.price = '';
      //     else this.ruleForm.price = newVal;
      //   }, 20);
      // },
      getProperties(ids) {
        this.$http.fetch('category/properties', {"ids": ids}).then((res) => {
          this.options = res.data;
        });
      },
      // getTotalMaterial(res) {
      //   this.ruleForm.materials = res.items
      //   // this.ruleForm.otherPrice = res.total
      // },
      handleCategoryChange() {
        this.ruleForm.properties = []
      }
    }
  }
</script>