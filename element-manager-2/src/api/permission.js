import request from "@/network/request";

export function queryAll() {
  return request({
    url: "/web/per",
    method: "get"
  });
}

export function queryParant() {
  return request({
    url: "/web/per/queryParant",
    method: "get"
  });
}

export function query(data) {
  return request({
    url: "/web/per/query",
    method: "get",
    params: data
  });
}

export function queryById(id) {
  return request({
    url: "/web/per/" + id,
    method: "get"
  });
}

export function getPermission(data) {
  return request({
    url: "/web/per/queryByUsername",
    method: "get",
    params: data
  });
}

export function queryByRoleId(roleId) {
  return request({
    url: "/web/per/queryByRoleId",
    method: "get",
    params: {
      roleId: roleId
    }
  });
}

export function queryByGroupId(groupId) {
  return request({
    url: "/web/per/queryByGroupId",
    method: "get",
    params: {
      groupId: groupId
    }
  });
}

export function addPer(data) {
  return request({
    url: "/web/per/add",
    method: "post",
    data
  });
}

export function updatePer(data) {
  return request({
    url: "/web/per/update",
    method: "post",
    data
  });
}

export function delPer(id) {
  return request({
    url: "/web/per/del/" + id,
    method: "get"
  });
}
