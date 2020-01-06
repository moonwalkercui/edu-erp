<template>

</template>
<script>
  export default {
    props: {},
    data() {
      return {
        iKnow: false,
        iKnowCourse: false,
      }
    },
    created() {
      this.checkNotice();
      setInterval(() => {
        this.checkNotice();
      }, 120000);
    },
    methods: {
      checkNotice() {
        var from_at = this.$cookie.getCookie('notify_from_at');
        this.$http.fetch("normal/checknotice", {
          from_time: from_at
        }).then((res) => {
          var opt = {};
          for (let i = 0; i < res.data.length; i++) {
            opt = {
              title: res.data[i].title,
              subject: res.data[i].subject,
              type: res.data[i].type,
              show: res.data[i].show,
            };
            if(this.iKnowCourse && opt.type=="newcourse") return;
            if (opt.show == 1) {
              ((o) => {
                 setTimeout(() => {
                  this.$notify({
                    title: o.title,
                    message: o.subject,
                    type: 'success',
                    duration: 20000,
                    position: 'bottom-right',
                    onClick: this.handleClick(o.type)
                  });
                }, 800 * i, o);
              })(opt)
            }
          }
        });
        if(this.iKnow) {
          from_at = Date.parse(new Date()) / 1000;
          this.$cookie.setCookie('notify_from_at', from_at, 1);
          this.iKnow = false;
        }
      },
      handleClick(type) {
        switch (type) {
          case "neworder":
            this.playAudio("neworder");
            return () => {
              this.iKnow = true;
              this.$router.push({ path: "/order/list" });
            };
          case "newrefund":
            return () => {
              this.iKnow = true;
              this.$router.push({ path: "/order/refund" });
            };
          case "newcourse":
            return () => {
              this.iKnow = true;
              this.iKnowCourse = true;
              this.$router.push({ path: "/course/mine" });
            };
          case "newproceed":
            return () => {
              this.iKnow = true;
              this.$router.push({ path: "/proceeds/list" });
            };
          case "newgiftapply":
            return () => {
              this.iKnow = true;
              this.$router.push({ path: "/gift/orders" });
            };
          default:
            return () => {
              this.iKnow = true;
              this.$router.push({ path: "/order/list" });
            };
        }
      },
      playAudio(type) {
        var mp3;
        switch (type) {
          case "neworder":
            mp3 = "audio/neworder.mp3";
            break;
          default:
            mp3 = "audio/neworder.mp3";
        }
        this.audio = new Audio();
        this.audio.src = mp3;
        this.audio.play();
      }
    }
  };
</script>