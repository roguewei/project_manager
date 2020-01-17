import request from "@/network/request";

export function getParameter(data) {
  return request({
    url: "/web/params",
    method: "get",
    params: data
  });
}
