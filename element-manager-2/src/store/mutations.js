export default {
  setUserInfo(state, user) {
    state.userInfo = user;
    console.log(state.userInfo);
  },
  setMeetingId(state, meetingId) {
    state.meetingId = meetingId;
  }
};
