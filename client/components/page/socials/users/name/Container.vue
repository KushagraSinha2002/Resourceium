<template>
  <div class="w-full px-4 my-5">
    <page-socials-users-name-card
      v-if="account"
      :account="account"
      :followers="followers"
      :following="following"
      @refreshStatus="$fetch()"
    ></page-socials-users-name-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      account: null,
      followers: [],
      following: [],
    }
  },
  async fetch() {
    const username = this.$route.params.username
    const details = await this.$axios.$get(`/accounts/${username}`)
    this.account = details
    const id = details.id
    const followers = await this.$axios.$get(`/follow/followers/${id}`)
    this.followers = followers
    const following = await this.$axios.$get(`/follow/following/${id}`)
    this.following = following
  },
}
</script>
