<template>
  <div>
    <div>All uploads</div>
    <div>
      <div v-for="(file, index) in files" :key="index">
        <div class="hover:underline">
          <a :href="getFileUrl(file)">{{ file.name }}</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { resolveURL } from 'ufo'

export default {
  data() {
    return {
      files: [],
      storageServer: process.env.storageServer,
    }
  },
  mounted() {
    this.$axios.$get('/uploads/all').then((resp) => {
      this.files = resp
    })
  },
  methods: {
    getFileUrl(file) {
      return resolveURL(this.storageServer, file.url)
    },
  },
}
</script>
