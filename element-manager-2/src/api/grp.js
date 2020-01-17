import request from "@/network/request";

export function saveGur(grp) {
  return request({
    url: "/web/grp/save",
    method: "post",
    data: grp
  });
}
