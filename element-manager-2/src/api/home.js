import request from "@/network/request";

export function getHomeInfo() {
  return request({
    url: "/web/home",
    method: "get"
  });
}
