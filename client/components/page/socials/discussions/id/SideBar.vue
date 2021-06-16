<template>
  <div class="p-3 space-y-4 xl:px-5">
    <div>
      <div
        class="text-lg underline cursor-pointer font-poppins"
        @click="handleCopy"
      >
        Copy invite
      </div>
      <div class="text-warm-gray-800 font-poppins">
        {{ discussion.inviteString }}
      </div>
    </div>
    <div>
      <div class="text-lg cursor-pointer font-poppins">
        <span class="underline">Participants</span>
        <span class="font-semibold">{{ accounts.length }}</span>
      </div>
      <div
        v-for="account in accounts"
        :key="account.id"
        :data-account-id="account.id"
      >
        <ig-icon
          name="x"
          variant="danger"
          size="sm"
          class="cursor-pointer"
          @click.native="removeAccount(account.id)"
        ></ig-icon>
        <h2 class="inline text-warm-gray-800 font-poppins">
          <NuxtLink
            :to="{
              name: 'socials-users-username',
              params: { username: `@${account.username}` },
            }"
            class="hover:underline"
          >
            @{{ account.username }}
          </NuxtLink>
        </h2>
      </div>
    </div>
    <div>
      <div class="text-lg underline cursor-pointer font-poppins">
        Created By
      </div>
      <div>
        <h2 class="text-warm-gray-800 font-poppins">
          <NuxtLink
            :to="{
              name: 'socials-users-username',
              params: { username: `@${discussion.createdBy.username}` },
            }"
            class="hover:underline"
          >
            @{{ discussion.createdBy.username }}
          </NuxtLink>
        </h2>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: { discussion: { type: Object, required: true } },
  data() {
    return {
      accounts: [],
    }
  },
  async mounted() {
    await this.getAccounts()
  },
  methods: {
    async getAccounts() {
      const resp = await this.$axios.$get(
        `/discussions/accounts/${this.discussion.id}`
      )
      this.accounts = resp
    },
    async removeAccount(accountID) {
      try {
        await this.$axios.$delete(
          `/discussions/remove-account/${this.discussion.id}/${accountID}`
        )
        this.$addAlert({
          message: 'Removed from discussion',
          type: 'success',
        })
        await this.getAccounts()
      } catch (e) {
        this.$addAlert({
          message: e.response.data,
          type: 'danger',
        })
      }
    },
    async handleCopy() {
      try {
        await this.$copyText(this.discussion.inviteString)
        this.$addAlert({
          message: 'Invite string copied to clipboard',
          type: 'success',
        })
      } catch (e) {
        this.$addAlert({
          message: 'Unable to copy to clipboard',
          type: 'danger',
        })
      }
    },
  },
}
</script>
