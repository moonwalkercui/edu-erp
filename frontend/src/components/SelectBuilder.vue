<template>
    <el-select :placeholder="placeholderText" :size="size" v-model="newVal" :multiple="isMultiple" filterable clearable :style="styles" @change="handleChange">
        <!--<el-option key="0" label="请选择" value="" v-if="showAll"></el-option>-->
        <template v-if="table=='user'">
            <el-option v-for="(item, index) in list"
                       :key="index+1"
                       :label="item.name"
                       :value="item.username">
            </el-option>
        </template>
        <template  v-else-if="table=='member' || table=='mymember'">
            <el-option v-for="(item, index) in list"
                       :key="index+1"
                       :label="item.nick_name + (item.name ? ' (' + item.name + ')' : '')"
                       :value="item.member_id">
            </el-option>
        </template>
        <!--<template  v-else-if="table=='product'">-->
            <!--<el-option v-for="(item, index) in list"-->
                       <!--:key="index+1"-->
                       <!--:label="item.name"-->
                       <!--:value="item.id">-->
            <!--</el-option>-->
        <!--</template>-->
        <template v-else>
            <el-option v-for="(item, index) in list"
                       :key="index+1"
                       :label="item.name"
                       :value="item.id">
            </el-option>
        </template>
    </el-select>
</template>
<script>
    import {find} from 'lodash'
    export default {
        props: {
            value: {},
            styles: {},
            condition: {},
            isMultiple: {},
            placeholderText: {default:'请选择'},
            size:{default:'mini'},
            table: {
                type: String,
                required: true
            }
//            , showAll: {
//                default: true
//            }
        },
        data() {
            return {
                newVal: this.isMultiple ? [] : '',
                list: [],
                urlParams: {},
                pk:''
            }
        },
        watch: {
            value: function (val) {
                if( ( (this.isMultiple && val.length != 0) || ( typeof this.isMultiple == 'undefined' && val != '') ) && this.list.length == 0){
                    this.getData();
                }else{
                    this.newVal = val;
                }
            },
            condition: function(val) {
                this.urlParams = val;
                this.getData();
            }
        },
        created(){
            if(typeof this.condition == 'undefined' ) this.getData();
        },
        methods: {
            handleChange(){
                this.$emit('input', this.newVal)
            },
            getData(){
                var path;
                switch (this.table){
                    case 'user':        path = 'user/table'; this.pk = 'username'; break;
                    case 'member':      path = 'member/table';  this.pk = 'member_id';break;
                    case 'mymember':    path = 'member/mine';  this.pk = 'member_id';break;
                    case 'class':       path = 'class/table';  this.pk = 'id';break;
                    case 'division':    path = 'org/divisions';  this.pk = 'id';break;
                    case 'product':     path = 'product/table';  this.pk = 'id';break;
                    case 'catetype':    path = 'type/table';  this.pk = 'id';break;
                    case 'room':        path = 'room/table'; this.pk = 'id'; break;
                    case 'material':    path = 'material/table';  this.pk = 'id';break;
                    case 'goodscategery':    path = 'shop/category';  this.pk = 'id';break;
                    case 'giftcategory':    path = 'gift/category';  this.pk = 'id';break;
                    case 'category':    path = 'category/table';  this.pk = 'id';break;
                    case 'voucher':    path = 'voucher/table';  this.pk = 'id';break;
                    case 'gift':    path = 'gift/table';  this.pk = 'id';break;
                    case 'region':    path = 'normal/regions';  this.pk = 'id';break;
                    case 'logtype':    path = 'setting/getlogtype';  this.pk = 'id';break;
                    default:            alert('缺少组件参数'); this.pk = 'id'; break;
                }
                this.$http.fetch(path, Object.assign({active: 'yes'},this.urlParams)).then((res) => {
                    var checkVal , obj = {};
                    this.list = res.data;
                    obj[this.pk] = this.value;
                    checkVal = find(this.list, obj);
                    // 如果没有获取到结果，或者初始值不在结果集中，则清空这个输入框值
                    if(this.list.length==0 ||  checkVal==null) {
                        this.newVal = this.isMultiple ? [] : '';
                        this.handleChange();
                    } else {
                        this.newVal = this.value;
                    }
                });
            }
        }
    };
</script>