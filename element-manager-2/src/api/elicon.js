import request from "@/network/request";

export function getIcon(data) {
  return request({
    url: "/web/icon/query",
    method: "get",
    params: data
  });
}

export function getIconAll() {
  return request({
    url: "/web/icon",
    method: "get"
  });
}

export function getIconById(id) {
  return request({
    url: "/web/icon/" + id,
    method: "get"
  });
}

export function addIcon(data) {
  return request({
    url: "/web/icon",
    method: "post",
    data
  });
}

export function updateIcon(data) {
  return request({
    url: "/web/icon/update",
    method: "post",
    data
  });
}

export function delIcon(id) {
  return request({
    url: "/web/icon/del/" + id,
    method: "get"
  });
}
