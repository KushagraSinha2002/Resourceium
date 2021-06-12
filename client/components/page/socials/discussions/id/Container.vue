<template>
  <div class="w-full h-full">
    <div v-if="!initialLoad" ref="scrollContainer" class="overflow-auto h-52">
      <div v-if="pageNumber == totalPages">That's it</div>
      <div ref="sentinel" class="sentinel"></div>
      <div ref="listContainer" class="list-container">
        <div v-for="post in posts" :key="post.id" class="h-10 list-item">
          {{ post.id }}
        </div>
      </div>
    </div>
    <div v-else class="initialLoad">INITIAL LIST LOADING .....</div>
    <div>{{ pageNumber }} {{ totalPages }} {{ posts.length }}</div>
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
  },
  destroyed() {
    if (this.listEndObserver) {
      this.listEndObserver.disconnect()
    }
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
  },
}
</script>
