<template>
    <section>
        <div class="line page-line"></div>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" size="small" label-width="150px" class="page-form-large">
            <el-form-item label="商品名称" prop="name">
                <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>
    
            <el-form-item label="所属分类" prop="cate">
                <select-builder table="goodscategery" v-model="ruleForm.cate"></select-builder>
            </el-form-item>
      
            <el-form-item label="价格" prop="price" >
              <el-input type="number" v-model="ruleForm.price"></el-input>
            </el-form-item>

             <el-form-item label="库存" prop="storage" >
              <el-input type="number" v-model="ruleForm.storage"></el-input>
            </el-form-item>

            <el-form-item label="添加图片" prop="image">
                <image-uploader :imageUrl="ruleForm.image" :limit="1" @upload="handleUploadedImage" :autoCrop="true" :cropWidth="500" :cropHeight="500"></image-uploader>
            </el-form-item>

            <el-form-item label="排序权重" prop="sort" >
              <el-input type="number" v-model="ruleForm.sort" placeholder="数值越大越靠前"></el-input>
            </el-form-item>
            
            <el-form-item label="状态" prop="status">
                <radio-status v-model="ruleForm.status" valueDefault="上架" statusName="product"></radio-status>
            </el-form-item>
      
            <el-form-item>
                <!-- <el-button type="primary" @click="submitForm('ruleForm')"> 提 交</el-button> -->
                <reqButton @handleReq = "submitForm('ruleForm')" />
            </el-form-item>

        </el-form>
    </section>
</template>
<script>
  // import _ from 'lodash'
  // import selectCategory from '@/components/SelectCategory'
  import radioStatus from '@/components/RadioStatus'
  // import checkboxDivision from '@/components/CheckboxDivision'
  // import productSpecification from '@/components/ProductSpecification'
  // import imageDraggableUploader from '@/components/ImageDraggableUploader'
  import selectBuilder from '@/components/SelectBuilder'
  import imageUploader from '@/components/ImageUploader'
  export default {
    components: {
      // "quillEditor": Editor,
      // selectCategory,
      radioStatus,
      // checkboxDivision,
      // imageDraggableUploader,
      // productSpecification,
      selectBuilder,
      imageUploader,
      // ProductLinkMaterialSelect
    },
    data() {
      return {
        ruleForm: {
          id: '',
          name: '',
          cate: '',
          price: '',
          storage: 999,
          sort: 0,
          status: '上架',
          image: '',
        },
        // selectMaterials:[],
        rules: {
          name: [{required: true, message: '请输入名称', trigger: 'blur'}],
          cate: [{required: true, message: '请选择分类', trigger: 'blur'}],
          price: [{required: true, message: '请输入价格', trigger: 'blur'}],
          image: [{required: true, message: '请上传图片', trigger: 'blur'}],
          storage: [{required: true, message: '请输入库存', trigger: 'blur'}],
        },
        dialogImageUrl: '',
        dialogImageVisible: false,
      };
    },
    created() {
      if (this.$route.params.id) this.getData();
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$http.post("shop/savegoods", this.ruleForm).then((res) => {
              if (res.status == 'success') {
                this.$message({message: res.msg, type: 'success'});
                this.$router.push('/shop/goods');
              }
            });
          } else {
            this.$message.error('提交失败!');
            return false;
          }
        });
      },
      getData() {
        this.$http.fetch('shop/findgoods', {"id": this.$route.params.id}).then((res) => {
          this.ruleForm.id = res.data.id;
          this.ruleForm.name = res.data.name;
          this.ruleForm.cate = res.data.category_id;
          this.ruleForm.image = res.data.image;
          this.ruleForm.status = res.data.status;
          this.ruleForm.storage = res.data.storage;
          this.ruleForm.sort = res.data.sort;
          this.ruleForm.price = res.data.price;
        });
      },
      handleUploadedImage(image) {
        this.ruleForm.image = image;
      },
    
    }
  }
</script>