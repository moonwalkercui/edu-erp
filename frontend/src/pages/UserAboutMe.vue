<template>
    <section>
    </section>
</template>
<script>
    export default {
        data() {
            return {
                name: '我的资料'
            };
        },
        created(){
            this.$router.push('/user/view/' + this.$cookie.fetchJson('_userInfo').uname);
        },
        methods: {
        }
    }
</script>
