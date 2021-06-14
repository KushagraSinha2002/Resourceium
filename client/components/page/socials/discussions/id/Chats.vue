<template>
  <div v-if="!initialLoad" ref="scrollContainer">
    <div ref="sentinel" class="sentinel"></div>
    <div v-if="posts.length > 0">
      <div
        v-if="pageNumber == totalPages"
        class="mb-2 text-2xl text-center text-gray-700 border-2 border-dashed font-styled-code"
      >
        No More Messages
      </div>
      <div ref="listContainer">
        <page-socials-discussions-id-chat-element
          v-for="post in posts"
          :key="post.id"
          :post="post"
        ></page-socials-discussions-id-chat-element>
      </div>
    </div>
    <div v-else class="flex items-center justify-center h-full">
      <common-empty-data>
        <span>Seems like there are no posts here.</span>
        <span>
          <NuxtLink
            :to="{ name: 'folders' }"
            class="text-purple-800 underline md:no-underline md:hover:underline"
            >Visit</NuxtLink
          >
          a folder to share it.
        </span>
      </common-empty-data>
    </div>
  </div>
</template>

<script>
import { withQuery } from 'ufo'

export default {
  data() {
    return {
      posts: [],
      isLoadingMore: false,
      initialLoad: true,
      pageNumber: 0,
      pageCount: 10,
      totalPages: 0,
    }
  },
  async mounted() {
    const items = await this.fetchItemsAPI(0)
    this.totalPages = items.totalPages
    this.addPosts(items.content)
    this.pageNumber++
    this.initialLoad = false
    this.$nextTick().then(() => {
      const scrollContainer = this.$refs.scrollContainer
      scrollContainer.scrollTop = scrollContainer.scrollHeight
      // scroll down
      this.$nextTick().then(() => {
        // setup up observer after initial list loaded and scrolled all the way to the bottom
        this.setUpInterSectionObserver()
      })
    })
    this.interval = window.setInterval(this.fetchLatest, 3000)
  },
  destroyed() {
    if (this.listEndObserver) {
      this.listEndObserver.disconnect()
    }
    clearInterval(this.interval)
  },
  methods: {
    recordScrollPosition() {
      const node = this.$refs.scrollContainer
      this.previousScrollHeightMinusScrollTop =
        node.scrollHeight - node.scrollTop
    },
    restoreScrollPosition() {
      const node = this.$refs.scrollContainer
      node.scrollTop =
        node.scrollHeight - this.previousScrollHeightMinusScrollTop
    },
    setUpInterSectionObserver() {
      const options = {
        root: this.$refs.scrollContainer,
        margin: '10px',
      }
      this.listEndObserver = new IntersectionObserver(
        this.handleIntersection,
        options
      )
      this.listEndObserver.observe(this.$refs.sentinel)
    },
    handleIntersection([entry]) {
      if (entry.isIntersecting) {
        // console.log('sentinel intersecting')
      }
      if (entry.isIntersecting && !this.isLoadingMore) {
        // console.log('sentinel intersecting2')
        this.loadMore()
      }
    },
    addPosts(posts) {
      this.posts.unshift(...posts.reverse())
    },
    async loadMore() {
      if (this.pageNumber < this.totalPages) {
        this.isLoadingMore = true
        this.recordScrollPosition()
        const items = await this.fetchItemsAPI(this.pageNumber)
        this.pageNumber++
        this.addPosts(items.content)
        this.isLoadingMore = false
        this.$nextTick(() => {
          this.restoreScrollPosition()
        })
      } else {
        this.isLoadingMore = false
      }
    },
    fetchItemsAPI(pageNumber) {
      const id = this.$route.params.id
      const url = withQuery(`/post/discussion/${id}`, { page: pageNumber })
      return this.$axios.$get(url)
    },
    async fetchLatest() {
      const id = this.$route.params.id
      const resp = await this.$axios.$get(`/post/discussion/${id}/last`)
      if (resp.id !== this.posts[this.posts.length - 1].id) {
        this.posts.push(resp)
      }
    },
  },
}
</script>
