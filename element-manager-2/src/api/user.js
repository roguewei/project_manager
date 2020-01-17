import request from "@/network/request";

export function login(data) {
  return request({
    url: "/login",
    method: "post",
    data
  });
}

export function getUsers(data) {
  return request({
    url: "/web/user",
    method: "get",
    params: data
  });
}

export function getInfo(userId) {
  return request({
    url: "/web/user/" + userId,
    method: "get"
  });
}

export function add(data) {
  return request({
    url: "/web/user/add",
    method: "post",
    data
  });
}

export function update(data) {
  return request({
    url: "/web/user/update",
    method: "post",
    data
  });
}

export function logout() {
  return request({
    url: "/logout",
    method: "get"
  });
}
