package com.b2i.tontine.domain.account.entity

import com.b2i.tontine.domain.entity.common.BaseTableEntity
import com.b2i.tontine.domain.entity.common.Role
import com.b2i.tontine.domain.entity.embeddable.Address
import com.b2i.tontine.domain.entity.embeddable.Contact
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
@Table(name = "user_account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "UserType", length = 20)
open class User : BaseTableEntity, UserDetails {

    @Column(unique = true, nullable = false)
    private var username: String? = null

    private var password: String? = null

    var lastname: String? = ""

    var firstname: String? = ""

    @Column(nullable = false)
    open var locked = false

    @Column(nullable = false)
    open var enabled = false

    @Column(nullable = false)
    open var expired = false

    @Column(nullable = false)
    open var credentialsExpired = false

    @Transient
    @JsonIgnore
    private var authorities: MutableCollection<GrantedAuthority>? = null

    open var lang: String? = null

    @Embedded
    open var address: Address = Address()

    @Embedded
    var contact: Contact = Contact()

    constructor()

    constructor(username: String, password: String, roles: Collection<Role>) {
        this.username = username
        this.password = password
        this.roles = roles
    }

    constructor(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
        username: String,
        password: String,
        roles: Collection<Role>
    ) : this(username, password, roles) {
        this.firstname = firstname
        this.lastname = lastname
        this.contact.email = email
        this.contact.phone = phone
    }

    @JsonIgnore
    @ManyToMany(targetEntity = Role::class, fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "User_Role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: Collection<Role>? = null

    fun setAuthorities(
        authorities: MutableCollection<GrantedAuthority>?
    ) {
        this.authorities = authorities
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        if (authorities == null || authorities!!.isEmpty()) {
            authorities = HashSet()
            for (role in roles!!) {
                authorities?.add(SimpleGrantedAuthority(String.format("ROLE_%s", role.name)))
            }
        }
        return authorities!!
    }


    fun setUsername(username: String) {
        this.username = username
    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String {
        return username!!
    }

    override fun isAccountNonExpired(): Boolean {
        return !expired
    }

    override fun isAccountNonLocked(): Boolean {
        return !locked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return !credentialsExpired
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    @JsonGetter
    fun roleId(): Long {
        return roles!!.stream().findFirst().get().id
    }


    @PrePersist
    public override fun onPrePersist() {
        super.onPrePersist()
        lang = "fr"
        enabled = true
        password = BCryptPasswordEncoder().encode(password)
    }
}