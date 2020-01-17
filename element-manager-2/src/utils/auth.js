// import Cookies from 'js-cookie';

const TokenKey = "authorization";
const UserInfoKey = "userInfo";
const UserRoleKey = "userRole";
const UserGroupKey = "userGroup";

// ------------ token信息 -------------
export function getToken() {
  // return Cookies.get(TokenKey);
  return window.sessionStorage.getItem(TokenKey);
}

export function setToken(token) {
  // return Cookies.set(TokenKey, token);
  return window.sessionStorage.setItem(TokenKey, token);
}

export function removeToken() {
  // return Cookies.remove(TokenKey);
  return window.sessionStorage.removeItem(TokenKey);
}

// ------------ 用户信息 -------------
export function getUserInfo() {
  const userInfo = JSON.parse(window.sessionStorage.getItem(UserInfoKey));
  return userInfo;
}

export function setUserInfo(userInfo) {
  const userJson = JSON.stringify(userInfo);
  return window.sessionStorage.setItem(UserInfoKey, userJson);
}

export function removeUserInfo() {
  return window.sessionStorage.removeItem(UserInfoKey);
}

// ------------ 角色信息 -------------
export function getUserRole() {
  let roleString = window.sessionStorage.getItem(UserRoleKey);
  if (roleString === "undefined") return undefined;
  const role = JSON.parse(window.sessionStorage.getItem(UserRoleKey));
  return role;
}

export function setUserRole(role) {
  const roleJson = JSON.stringify(role);
  return window.sessionStorage.setItem(UserRoleKey, roleJson);
}

export function removeUserRole() {
  // return Cookies.remove(TokenKey);
  return window.sessionStorage.removeItem(UserRoleKey);
}

// ------------ 部门信息 -------------
export function getUserGroup() {
  let groupString = window.sessionStorage.getItem(UserGroupKey);
  if (groupString === "undefined") return undefined;
  console.log("================");

  const group = JSON.parse(window.sessionStorage.getItem(UserGroupKey));
  return group;
}

export function setUserGroup(group) {
  const groupJson = JSON.stringify(group);
  return window.sessionStorage.setItem(UserGroupKey, groupJson);
}

export function removeUserGroup() {
  // return Cookies.remove(TokenKey);
  return window.sessionStorage.removeItem(UserGroupKey);
}

export function removeAll() {
  removeToken();
  removeUserInfo();
  removeUserRole();
  return removeUserGroup();
}
