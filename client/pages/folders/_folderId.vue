<template>
  <div>{{ folder }}</div>
</template>

<script>
export default {
  data() {
    return {
      folder: null,
    }
  },
  async mounted() {
    this.folder = await this.getFolderDetails()
  },
  methods: {
    async getFolderDetails() {
      const params = this.$route.params
      const resp = await this.$axios.$get(`/folders/folder/${params.folderId}`)
      return resp
    },
  },
}
</script>
