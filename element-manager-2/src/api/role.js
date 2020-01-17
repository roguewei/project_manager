import request from "@/network/request";

export function getRoleList() {
  return request({
    url: "/web/role",
    method: "get"
  });
}

export function getRoleListParams(params) {
  return request({
    url: "/web/role/query",
    method: "get",
    params: params
  });
}

export function getRoleById(id) {
  return request({
    url: "/web/role/" + id,
    method: "get"
  });
}

export function getRoleByUserId(userId) {
  return request({
    url: "/web/role/queryByUserId/" + userId,
    method: "get"
  });
}

export function addRole(data) {
  return request({
    url: "/web/role/add",
    method: "post",
    data
  });
}

export function updateRole(data) {
  return request({
    url: "/web/role/update",
    method: "post",
    data
  });
}

export function delRole(userId) {
  return request({
    url: "/web/role/del/" + userId,
    method: "get"
  });
}
