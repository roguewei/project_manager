import request from "@/network/request";

export function getMyProject(data) {
  return request({
    url: "/web/gup",
    method: "get",
    params: data
  });
}

export function getMyParentProject(data) {
  return request({
    url: "/web/gup/queryMyParent",
    method: "get",
    params: data
  });
}
