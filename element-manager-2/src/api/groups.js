import request from "@/network/request";

export function getGroupList() {
  return request({
    url: "/web/group",
    method: "get"
  });
}

export function getGroupListParams(data) {
  return request({
    url: "/web/group//query",
    method: "get",
    params: data
  });
}

export function getGroupById(id) {
  return request({
    url: "/web/group/" + id,
    method: "get"
  });
}

export function getGroupByUserId(userId) {
  return request({
    url: "/web/group/queryByUserId/" + userId,
    method: "get"
  });
}

export function addGroup(data) {
  return request({
    url: "/web/group/add",
    method: "post",
    data
  });
}

export function updateGroup(data) {
  return request({
    url: "/web/group/update",
    method: "post",
    data
  });
}

export function delGroup(id) {
  return request({
    url: "/web/group/del/" + id,
    method: "get"
  });
}
