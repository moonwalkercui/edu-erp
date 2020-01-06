import Layout from '@/components/Layout'
// import LayoutSimple from '@/components/LayoutSimple'
import Login from '@/pages/Login'
import Dashboard from '@/pages/Dashboard'
// import Register from '@/pages/Register'
// import Switchover from '@/pages/Switchover'
// import CreateOrJoin from '@/pages/CreateOrJoin'
import UserCards from '@/pages/UserCards'
import UserList from '@/pages/UserList'
import UserApplication from '@/pages/UserApplication'
import UserEdit from '@/pages/UserEdit'
import UserAboutMe from '@/pages/UserAboutMe'
import UserView from '@/pages/UserView'
import MemberMine from '@/pages/MemberMine'
import ProductList from '@/pages/ProductList'
import ProductCreate from '@/pages/ProductCreate'
// import Badges from '@/pages/Badges'
import MemberList from '@/pages/MemberList'
import MemberStudent from '@/pages/MemberStudent'
import MemberRanking from '@/pages/MemberRanking'
import CourseList from '@/pages/CourseList'
import CourseMine from '@/pages/CourseMine'
import RoleList from '@/pages/RoleList'
import CourseCalendar from '@/pages/CourseCalendar'
import CourseCalendarMine from '@/pages/CourseCalendarMine'
import CourseSchedule from '@/pages/CourseSchedule'
import CourseScheduleWeek from '@/pages/CourseScheduleWeek'
// import CourseDuty from '@/pages/CourseDuty'
import CourseSignList from '@/pages/CourseSignList'
import CourseEdit from '@/pages/CourseEdit'
import OrderList from '@/pages/OrderList'
import OrderRefund from '@/pages/OrderRefund'
import OrderSalesman from '@/pages/OrderSalesman'
// import OrderMine from '@/pages/OrderMine'
// import OrderCreate from '@/pages/OrderCreate'
import RoleCreate from '@/pages/RoleCreate'
import Divisions from '@/pages/Divisions'
import DivisionsCoordinate from '@/pages/DivisionsCoordinate'
import DepartmentList from '@/pages/DepartmentList'
// import ClassList from '@/pages/ClassList'
// import RoomList from '@/pages/RoomList'
import CategoryList from '@/pages/CategoryList'
import CategoryTypes from '@/pages/CategoryTypes'
// import MaterialList from '@/pages/MaterialList'
// import MaterialApplications from '@/pages/MaterialApplications'
import MemberCreate from '@/pages/MemberCreate'
import ChartOrders from '@/pages/ChartOrders'
import ChartCourses from '@/pages/ChartCourses'
import ChartSigns from '@/pages/ChartSigns'
import ChartProducts from '@/pages/ChartProducts'
import ChartUsers from '@/pages/ChartUsers'
import ChartProceeds from '@/pages/ChartProceeds'
import Advertisements from '@/pages/Advertisements'
import Notification from '@/pages/Notification'
import Zone from '@/pages/Zone'
import GoodsList from '@/pages/GoodsList'
import GoodsCategory from '@/pages/GoodsCategory'
import GoodsCreate from '@/pages/GoodsCreate'
// import VoucherList from '@/pages/VoucherList'
// import VoucherLog from '@/pages/VoucherLog'
import RedPacketLog from '@/pages/RedPacketLog'
import RedPacketSetting from '@/pages/RedPacketSetting'
import GiftList from '@/pages/GiftList'
import GiftOrders from '@/pages/GiftOrders'
import ProceedsList from '@/pages/ProceedsList'
import Setting from '@/pages/Setting'
import Logs from '@/pages/Logs'
import WxMsgTemplate from '@/pages/WxMsgTemplate'
// import ProceedsMine from '@/pages/ProceedsMine'
// import ProceedsCreate from '@/pages/ProceedsCreate'
// import ProceedsItems from '@/pages/ProceedsItems'
// import ProceedsEdit from '@/pages/ProceedsEdit'
// import CourseCreate from '@/pages/CourseCreate'

// openTab为false的路径不会添加到动态导航里。比如有路径参数的不建议设置，title显示在页面顶部
let routes = [
  // { path:'/:orgcode', component: Login, meta: { openTab:false } },
  // { path:'/:orgcode/register', component: Register, meta: { openTab:false } },
  { path:'/', component: Login, meta: { openTab:false } },
  { path:'/i', component: Layout, children : [
    {path:'dashboard', component: Dashboard, name:'首 页', meta: {title:'首页', requireAuth:true, openTab:true, topMenu:1} },
  ]},
  { path:'/infomation', component: Layout, meta: { title:'信息'}, children : [
    { path:'advertisements', component: Advertisements, name:'广告管理', meta: { requireAuth:true, openTab:false, topMenu:1,title:'广告管理'} },
    { path:'notifications', component: Notification, name:'公告管理', meta: { requireAuth:true, openTab:false, topMenu:1,title:'公告管理'} },
    { path:'zone', component: Zone, name:'圈子管理', meta: { requireAuth:true, openTab:false, topMenu:1,title:'圈子管理'} },
  ]},
  // { path:'/service', component: LayoutSimple, meta: { title:'账号服务'}, children : [
    // { path:'createOrJoin', component: CreateOrJoin , meta: { requireAuth:true, openTab:false, topMenu:1} }, // 创建或加入组织
    // { path:'switchover', component: Switchover , meta: { requireAuth:true, openTab:false, topMenu:1} }, // 切换组织
    // { path:'orgCreate', component: OrgCreate , meta: { requireAuth:true, openTab:false, topMenu:1} }, // 创建组织
    // { path:'orgJoin', component: OrgJoin , meta: { requireAuth:true, openTab:false, topMenu:1} }, // 申请加入组织
  // ]},
  { path:'/user', component: Layout, meta: { title:'老师'}, children : [
    {path:'cards', component: UserCards, name:'通讯录', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'通讯录'} },
    {path:'list', component: UserList, name:'老师管理', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'老师管理'} },
    {path:'application', component: UserApplication, name:'申请列表', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'申请列表'} },
    {path:'edit/:uname', component: UserEdit, name:'修改老师资料', meta: { requireAuth:true, openTab:false, topMenu:1 ,title:'修改老师资料'} },
    {path:'me', component: UserAboutMe, name:'我的资料', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'我的资料'} },
    {path:'view/:uname', component: UserView, name:'查看资料', meta: { requireAuth:true, openTab:false, topMenu:1 ,title:'查看资料'} },
  ]},
  { path:'/org', component: Layout, meta: { title:'店面'}, children : [
    {path:'divisions', component: Divisions, name:'店面列表', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'店面管理'} },
    {path:'coordinate/:id/:lat/:lng', component: DivisionsCoordinate, name:'设置坐标', meta: { requireAuth:true, topMenu:1 ,title:'设置坐标'} },
  ]},
  { path:'/department', component: Layout, meta: { title:'部门'}, children : [
    {path:'list', component: DepartmentList, name:'部门列表', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'部门列表'} },
  ]},
  { path:'/member', component: Layout , meta: { title:'学员'}, children : [
    {path:'mine', component: MemberMine, name:'我的学员', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'我的学员'} },
    {path:'list', component: MemberList, name:'关注列表', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'关注列表'} },
    {path:'students', component: MemberStudent, name:'学员列表', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'学员列表'} },
    {path:'ranking', component: MemberRanking, name:'学员排名', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'学员排名'} },
    {path:'create', component: MemberCreate, name:'添加学员', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'添加学员'} },
    // {path:'edit/:mobile', component: MemberCreate, meta: { requireAuth:true, openTab:false, topMenu:1 ,title:'修改学员'} },
  ]},
  { path:'/setting', component: Layout , meta: { title:'系统管理'}, children : [
    {path:'index', component: Setting, name:'系统设置', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'系统设置'} },
    {path:'log', component: Logs, name:'操作日志', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'操作日志'} },
    {path:'msgtemplate', component: WxMsgTemplate, name:'消息模板', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'消息模板'} },
  ]},
  { path:'/product', component: Layout, meta: { title:'课程'},children : [
    {path:'list', component: ProductList, name:'课程管理', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'课程管理'} },
    {path:'create', component: ProductCreate, name:'添加课程', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'添加课程'} },
    {path:'edit/:id', component: ProductCreate, name:'编辑课程', meta: { requireAuth:true, openTab:false, topMenu:1 ,title:'编辑课程'} },
    // {path:'badges', component: Badges, name:'徽章管理', meta: { requireAuth:true, openTab:false, topMenu:1 ,title:'徽章管理'} },
  ]},
  { path:'/shop', component: Layout, meta: { title:'商城'},children : [
    {path:'goods', component: GoodsList, name:'商品管理', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'商品管理'} },
    {path:'category', component: GoodsCategory, name:'商品分类', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'商品分类'} },
    {path:'create/:id', component: GoodsCreate, name:'修改商品', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'修改商品'} },
    {path:'create', component: GoodsCreate, name:'添加商品', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'添加商品'} },
  ]},
  { path:'/gift', component: Layout, meta: { title:'礼品兑换'},children : [
    {path:'list', component: GiftList, name:'礼品管理', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'礼品管理'} },
    {path:'orders', component: GiftOrders, name:'兑换管理', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'兑换管理'} },
  ]},
  // { path:'/class', component: Layout, meta: { title:'班级'}, children : [
  //   {path:'list', component: ClassList, name:'班级列表', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'班级列表'} },
  //   {path:'rooms', component: RoomList, name:'教室管理', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'教室管理'} },
  // ]},
  // { path:'/material', component: Layout, meta: { title:'物料'}, children : [
  //   {path:'list', component: MaterialList, name:'物料列表', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'物料列表'} },
  //   {path:'application', component: MaterialApplications, name:'申请审核', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'申请审核'} },
  // ]},
  { path:'/proceeds', component: Layout, meta: { title:'财务'}, children : [
    {path:'list', component: ProceedsList, name:'收款与认款', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'收款与认款'} },
  //   {path:'mine', component: ProceedsMine, name:'我的应收款', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'我的应收款'} },
    // {path:'create', component: ProceedsCreate, name:'发起收款', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'发起收款'} },
  //   // {path:'edit/:sn', component: ProceedsEdit, name:'修改收款项', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'修改收款项'} },
  //   {path:'items', component: ProceedsItems, name:'收款项目', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'收款项目'} },
  ]},
  { path:'/category', component: Layout, meta: { title:'分类'}, children : [
    {path:'list', component: CategoryList, name:'分类管理', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'分类管理'} },
    {path:'types', component: CategoryTypes, name:'分类类型管理', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'分类类型管理'} },
  ]},
  // { path:'/voucher', component: Layout, meta: { title:'代金券管理'}, children : [
  //   {path:'list', component: VoucherList, name:'代金券列表', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'代金券列表'} },
  //   {path:'log', component: VoucherLog, name:'领取记录', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'使用记录'} },
  // ]},
  { path:'/course', component: Layout, meta: { title:'课时'}, children : [
    {path:'list', component: CourseList, name:'课时列表', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'课时列表'} },
    {path:'mine', component: CourseMine, name:'我的课时', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'我的课时'} },
    {path:'myCalendar', component: CourseCalendarMine, name:'我的课表', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'我的课表'} },
    {path:'calendar', component: CourseCalendar, name:'课时日历', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'课时日历'} },
    // {path:'edit/:id', component: CourseCreate, name:'修改课时', meta: { requireAuth:true, openTab:false, topMenu:1 ,title:'修改课时'} },
    {path:'signs', component: CourseSignList, name:'签到记录', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'签到记录'} },
    {path:'edit/:id', component: CourseEdit, name:'编辑课时', meta: { requireAuth:true, openTab:false, topMenu:1 ,title:'编辑课时'} },
    {path:'schedule', component: CourseSchedule, name:'批量排课', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'批量排课'} },
    {path:'scheduleweek', component: CourseScheduleWeek, name:'按周排课', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'按周排课'} },
    // {path:'schedule', component: CourseDuty, name:'排课', meta: { requireAuth:true, openTab:false, topMenu:1 ,title:'排课'} },
  ]},
  { path:'/order', component: Layout, meta: { title:'订单'}, children : [
    {path:'list', component: OrderList, name:'订单列表', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'订单列表'} },
    {path:'salesman', component: OrderSalesman, name:'业务员订单', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'业务员订单'} },
    {path:'refund', component: OrderRefund, name:'退款管理', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'退款管理'} },
    // {path:'mine', component: OrderMine, name:'我的成单', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'我的成单'} },
    // {path:'create', component: OrderCreate, name:'成单上报', meta: { requireAuth:true, openTab:true, topMenu:1 ,title:'成单上报'} },
    // {path:'edit/:sn', component: OrderCreate, name:'修改订单', meta: { requireAuth:true, openTab:false, topMenu:1 ,title:'修改订单'} },
  ]},
  { path:'/rbac', component: Layout, meta: { title:'权限'}, children : [
    {path:'roles', component: RoleList, name:'角色管理', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'角色管理'} },
    {path:'roleCreate', component: RoleCreate, name:'添加角色', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'添加角色'} },
    {path:'roleEdit/:id', component: RoleCreate, name:'编辑角色', meta: { requireAuth:true, openTab:false, topMenu:2 ,title:'编辑角色'} },
  ]},
  { path:'/redpackage', component: Layout, meta: { title:'红包管理'}, children : [
    {path:'log', component: RedPacketLog, name:'红包出入账', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'红包出入账'} },
    {path:'setting', component: RedPacketSetting, name:'红包规则设置', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'红包规则设置'} },
  ]},
  { path:'/statistics', component: Layout, meta: { title:'统计排名'}, children : [
    {path:'orders', component: ChartOrders, name:'订单统计', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'订单统计'} },
    {path:'courses', component: ChartCourses, name:'课时统计', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'课时统计'} },
    {path:'signs', component: ChartSigns, name:'签到统计', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'签到统计'} },
    {path:'users', component: ChartUsers, name:'老师排名', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'老师排名'} },
    {path:'products', component: ChartProducts, name:'排 名', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'排 名'} },
    {path:'proceeds', component: ChartProceeds, name:'收款统计', meta: { requireAuth:true, openTab:true, topMenu:2 ,title:'收款统计'} },
  ]},
];
// 其他插件开发：作业插件，活动调查插件，其他教学插件等。
export default routes;