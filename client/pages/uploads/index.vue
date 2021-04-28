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
import { resolveURL, withQuery } from 'ufo'

export default {
  data() {
    return {
      files: [],
      baseUrl: process.env.baseUrl,
    }
  },
  mounted() {
    this.$axios.$get('/uploads/all').then((resp) => {
      this.files = resp
    })
  },
  methods: {
    getFileUrl(file) {
      return withQuery(resolveURL(this.baseUrl, 'uploads', 'download'), {
        userId: String(file.account.id),
        filename: file.slug,
      })
    },
  },
}
</script>
