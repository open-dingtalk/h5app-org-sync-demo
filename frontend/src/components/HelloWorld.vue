<template>
  <div class="hello">
    <div class="btn" @click="sync"></div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  methods: {
    sync() {
      axios.put("/sync").then((response) => {
        if (response.data.success) {
          this.$alert('同步成功');
        }else{
          this.$alert('同步失败');
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.hello{
  width: 100%;
  height: 100%;
  background-image: url("../assets/bj.png");
  background-size: 100% 100%;
}
.btn{
  width: 270px;
  height: 90px;
  border-radius: 130px;
  background-image: url("../assets/btn.png");
  background-size: 100% 100%;
  cursor: pointer;
  position: absolute;
  bottom: 20px;
  left: 50px;
}
</style>
