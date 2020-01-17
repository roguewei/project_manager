import request from "@/network/request";

export function saveGur(gur) {
  return request({
    url: "/web/gur",
    method: "post",
    data: gur
  });
}
