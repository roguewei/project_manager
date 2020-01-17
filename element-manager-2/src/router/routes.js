const Login = () => import("@/views/Login/Login");
const Home = () => import("@/views/Home/Home");
const Welcome = () => import("@/views/Home/children/welcome/Welcome.vue");
const User = () => import("@/views/Home/children/user/User");
const Permission = () => import("@/views/Home/children/permission/Permission");
const Role = () => import("@/views/Home/children/role/Role");
const Group = () => import("@/views/Home/children/group/Group");
const Parameter = () => import("@/views/Home/children/Parameter");
const ElIcon = () => import("@/views/Home/children/elicon/ElIcon");
const Project = () => import("@/views/Home/children/project/Project.vue");
const MyProject = () => import("@/views/Home/children/project/MyProject.vue");

const routes = [
  {
    path: "/",
    redirect: "/login"
  },
  {
    path: "/login",
    component: Login
  },
  {
    path: "/home",
    component: Home,
    // 当来到/home页面时进行重定向
    redirect: "/welcome",
    children: [
      {
        path: "/welcome",
        component: Welcome
      },
      {
        path: "/web/user",
        component: User
      },
      {
        path: "/parameter",
        component: Parameter
      },
      {
        path: "/web/per",
        component: Permission
      },
      {
        path: "/web/role",
        component: Role
      },
      {
        path: "/web/group",
        component: Group
      },
      {
        path: "/web/elicon",
        component: ElIcon
      },
      {
        path: "/web/project",
        component: Project
      },
      {
        path: "/web/myproject",
        component: MyProject
      }
    ]
  }
];

export default routes;
