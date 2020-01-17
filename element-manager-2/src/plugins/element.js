import Vue from "vue";
import {
  Button,
  Form,
  FormItem,
  Input,
  Container,
  Header,
  Aside,
  Main,
  Menu,
  Submenu,
  MenuItemGroup,
  MenuItem,
  Breadcrumb,
  BreadcrumbItem,
  Card,
  Row,
  Col,
  Table,
  TableColumn,
  Switch,
  Tooltip,
  Pagination,
  Dialog,
  Select,
  Option,
  Radio,
  Upload,
  MessageBox,
  Loading,
  Tree,
  Progress,
  DatePicker,
  Slider
} from "element-ui";
// 弹框提示组件
import { Message } from "element-ui";

Vue.use(Button);
Vue.use(Form);
Vue.use(FormItem);
Vue.use(Input);
Vue.use(Container);
Vue.use(Header);
Vue.use(Aside);
Vue.use(Main);
Vue.use(Menu);
Vue.use(Submenu);
Vue.use(MenuItemGroup);
Vue.use(MenuItem);
Vue.use(Breadcrumb);
Vue.use(BreadcrumbItem);
Vue.use(Card);
Vue.use(Row);
Vue.use(Col);
Vue.use(Table);
Vue.use(TableColumn);
Vue.use(Switch);
Vue.use(Tooltip);
Vue.use(Pagination);
Vue.use(Dialog);
Vue.use(Select);
Vue.use(Option);
Vue.use(Radio);
Vue.use(Upload);
Vue.use(Loading);
Vue.use(Tree);
Vue.use(Progress);
Vue.use(DatePicker);
Vue.use(Slider);

// 弹框需要全局挂载,这样每个组件都可以直接通过this.$message来调用
Vue.prototype.$message = Message;
Vue.prototype.$confirm = MessageBox.confirm;
