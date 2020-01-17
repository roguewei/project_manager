import request from "@/network/request";

export function getProject(data) {
  return request({
    url: "/web/project",
    method: "get",
    params: data
  });
}

export function getProjectAll() {
  return request({
    url: "/web/project",
    method: "get",
    params: {
      page: "1",
      length: "1000"
    }
  });
}

export function getProjectParent(id) {
  return request({
    url: "/web/project/queryParent",
    method: "get",
    params: {
      id: id
    }
  });
}

export function getProjectById(id) {
  return request({
    url: "/web/project/" + id,
    method: "get"
  });
}

export function addProject(data) {
  return request({
    url: "/web/project",
    method: "post",
    data
  });
}

export function addMyProject(data, gupForm) {
  return request({
    url: "/web/project/addMyProject",
    method: "post",
    data,
    params: gupForm
  });
}

export function updateProject(data) {
  return request({
    url: "/web/project/update",
    method: "post",
    data
  });
}

export function delProjectById(id) {
  return request({
    url: "/web/project/del/" + id,
    method: "get"
  });
}
